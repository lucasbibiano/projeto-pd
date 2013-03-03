package lb.core;

import java.util.ArrayList;

import lb.messages.MessageContext;
import lb.messages.MessageParser;
import network.Connection;
import network.Message;
import network.MessageListener;

public class ServerConnection implements MessageListener {

	private Connection connection;
	private ArrayList<String> users;
	private LoadBalancer loadBalancer;
	private int capacity;
	
	private String serverIP;
	private int serverPort;

	public ServerConnection(LoadBalancer loadBalancer, Connection conn, String serverIP, int serverPort) {
		this.connection = conn;
		this.loadBalancer = loadBalancer;
		this.serverIP = serverIP;
		this.serverPort = serverPort;

		users = new ArrayList<String>();
		
		connection.setMessageListener(this);
		connection.openConnection();

		connection.listen();
	}

	@Override
	public void messageReceived(Message message) {
		message.setConnection(connection);
		MessageParser.parseMessage(new MessageContext(message, loadBalancer));
	}
	
	public void newConnectedUser(String user) {
		users.add(user);
	}
	
	public void disconnectedUser(String user) {
		users.remove(user);
	}
	
	public int getUsersConnectedNum() {
		return users.size();
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void closeConnection() {
		connection.closeConnection();
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

}
