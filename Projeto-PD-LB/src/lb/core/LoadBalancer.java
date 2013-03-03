package lb.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import lb.messages.MessageContext;
import lb.messages.MessageParser;

import utils.ConfigManager;
import utils.TextAreaLogger;

import network.Connection;
import network.ConnectionListener;
import network.ConnectionManager;
import network.Message;
import network.MessageListener;
import network.TCPConnectionManager;

public class LoadBalancer implements ConnectionListener {
	
	private ConnectionManager listener;
	
	private ArrayList<ServerConnection> serverConnections;
	private ArrayList<Connection> loadBalancerConnections;
	
	private ArrayList<Connection> connections;
	
	private int port;
	
	public LoadBalancer() {
		port = (Integer) ConfigManager.getConfig("port"); 
		
		connections = new ArrayList<Connection>();
		serverConnections = new ArrayList<ServerConnection>();
		loadBalancerConnections = new ArrayList<Connection>();
		
		try {
			listener = new TCPConnectionManager(new ServerSocket(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listener.setConnectionListener(this);
		
		TextAreaLogger.getInstance().log("Iniciando LB...");
				
		listener.listenForConnections();
	}
	
	public void authenticateServer(Connection conn, int capacity, String ip, int port) {
		TextAreaLogger.getInstance().log("Servidor registrado: " + ip + ":" + port);
		
		ServerConnection sConn = new ServerConnection(this, conn, ip, port);
		sConn.setCapacity(capacity);
		
		serverConnections.add(sConn);		
	}

	public void newConnectionOnServer(Connection conn, String user) {
		for (ServerConnection server: serverConnections) {
			if (server.getConnection().equals(conn)) {
				server.newConnectedUser(user);
				break;
			}
		}
	}

	public void closedConnectionOnServer(Connection conn, String user) {
		for (ServerConnection server: serverConnections) {
			if (server.getConnection().equals(conn)) {
				server.disconnectedUser(user);
				break;
			}
		}
	}
	
	public void authenticateLoadBalancer(Connection conn) {
		loadBalancerConnections.add(conn);
	}

	public String[] getAllActiveServers() {
		if (serverConnections.size() == 0) {
			String[] result = {"none"};
			return result;
		}
		
		String[] servers = new String[serverConnections.size()];
		
		int i = 0;
		
		for (ServerConnection server: serverConnections) {
			servers[i] = server.getServerIP() + ":" + server.getServerIP(); 
			i++;
		}
		
		return servers;
	}
	
	public String suggestServer() {
		for (ServerConnection server: serverConnections) {
			if (server.getUsersConnectedNum() < server.getCapacity()) {
				return server.getServerIP() + ":" + server.getServerPort();
			}
		}
		
		return "none";
	}
	
	public String[] getAllActiveLoadBalancers() {
		String[] lbs = new String[loadBalancerConnections.size()];
		int i = 0;
		
		for (Connection conn: loadBalancerConnections) {
			lbs[i] = conn.getHost().getHostAddress() + ":" + conn.getPort(); 
			i++;
		}
		
		return lbs;
	}
	
	public void registerAsLB() {
		Message msg = new Message("RegisterAsLB", "senha_secreta");
		
		for (Connection conn: loadBalancerConnections) {
			conn.sendMessage(msg);
		}
	}

	@Override
	public void newConnection(final Connection connection) {
		final LoadBalancer temp = this;
		
		connection.setMessageListener(new MessageListener() {
			
			@Override
			public void messageReceived(Message message) {
				message.setConnection(connection);
				MessageParser.parseMessage(new MessageContext(message, temp));
			}
		});
		
		connection.openConnection();

		connection.listen();
		
		connections.add(connection);
	}

	@Override
	public void connectionClosed(Connection connection) {
		connections.remove(connection);
	}
}
