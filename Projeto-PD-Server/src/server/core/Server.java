package server.core;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import network.Connection;
import network.ConnectionListener;
import network.ConnectionManager;
import network.Message;
import network.MessageListener;
import network.TCPConnection;
import network.TCPConnectionManager;
import system.core.User;
import utils.ConfigManager;
import utils.TextAreaLogger;

public class Server implements ConnectionListener {
	private ConnectionManager listener;
	
	private Connection loadBalancerConnection;
	
	private UserConnection[] connections;
	private int port;
		
	private int next;
	private int capacity;
					
	public Server() {

	}
	
	public void start() {
		port = (Integer) ConfigManager.getConfig("server_port"); 
		
		capacity = (Integer) ConfigManager.getConfig("server_capacity");
		next = 0;
		
		connections = new UserConnection[capacity];
		
		//TODO: mudar aqui que conexao abrir
		try {
			listener = new TCPConnectionManager(new ServerSocket(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tellLoadBalancer("127.0.0.1", 5333);
		
		listener.setConnectionListener(this);
		
		TextAreaLogger.getInstance().log("Starting server on port " + port + "...");
				
		listener.listenForConnections();
	}
	
	private void tellLoadBalancer(String host, int port) {
		
		try {
			loadBalancerConnection = new TCPConnection(new Socket(host, port));
			loadBalancerConnection.openConnection();
			loadBalancerConnection.listen();
			
			loadBalancerConnection.setMessageListener(new MessageListener() {
				
				@Override
				public void messageReceived(Message message) {
					if (message.getCommand().equals("OK")) {
						loadBalancerConnection.closeConnection();
						return;
					}
				}
			});
			
			loadBalancerConnection.sendMessage(new Message("RegisterAsServer", "senha_secreta",
				String.valueOf(capacity), InetAddress.getLocalHost().getHostAddress(), String.valueOf(this.port)));
		} catch (Exception e) {
			TextAreaLogger.getInstance().log("Trying to registrate as a server...");
			Thread.yield();
			tellLoadBalancer(host, port);
			return;
		}
	}
	
	public void close() {
		loadBalancerConnection.closeConnection();
	}
	
	@Override
	public void newConnection(Connection connection) {		
		connections[next] = new UserConnection(this, connection);
			
		TextAreaLogger.getInstance().log("Accepted connection from " + connection.getHost().toString() +
			" on port " + connection.getPort());
				
		next++;
	}
	
	@Override
	public void connectionClosed(Connection connection) {
		for (int i = 0; i < next; i++) {
			if (connection.equals(connections[i].getConnection())) {
				connections[i] = null;
								
				break;
			}
		}
	}
	
	public void authenticateConnection(Connection conn, User user) {
		for (int i = 0; i < next; i++) {
			if (conn.equals(connections[i].getConnection())) {
				connections[i].setUser(user);
					
				break;
			}
		}
	}

	public void logoff(User user) {
		for (int i = 0; i < next; i++) {
			if (connections[i].getUser().equals(user)) {
				connections[i].setUser(null);
								
				break;
			}
		}
	}
}
