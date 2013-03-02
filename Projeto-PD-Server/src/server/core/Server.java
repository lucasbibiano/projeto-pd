package server.core;

import java.io.IOException;
import java.net.ServerSocket;

import network.Connection;
import network.ConnectionListener;
import network.ConnectionManager;
import network.TCPConnectionManager;
import system.core.User;
import utils.ConfigManager;
import utils.TextAreaLogger;

public class Server implements ConnectionListener {
	private ConnectionManager listener;
	
	private UserConnection[] connections;
		
	private int next;
	private int capacity;
					
	public Server() {

	}
	
	public void start() {
		int port = (Integer) ConfigManager.getConfig("server_port"); 
		
		capacity = (Integer) ConfigManager.getConfig("server_capacity");
		next = 0;
		
		connections = new UserConnection[capacity];
		
		//TODO: mudar aqui que conexao abrir

		/* TCP
		try {
			listener = new TCPConnectionManager(new ServerSocket(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		try {
			listener = new TCPConnectionManager(new ServerSocket(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listener.setConnectionListener(this);
		
		TextAreaLogger.getInstance().log("Starting server...");
				
		listener.listenForConnections();
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
