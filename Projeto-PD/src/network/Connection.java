package network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import utils.TextAreaLogger;

public abstract class Connection {
	private MessageListener messageListener;
	
	private InetAddress host;
	private int port;
	
	private HashMap<Integer, Message> messagesWaitingConfirm;

	public Connection(String host, int port) {
		try {
			this.host = InetAddress.getByName(host);
			this.port = port;
		} catch (UnknownHostException e) {
		}
		
		messagesWaitingConfirm = new HashMap<Integer, Message>();
	}

	public abstract void openConnection();
	public abstract void sendMessage(Message message);
	public abstract void listen();
	public abstract void closeConnection();
	
	public InetAddress getHost() {
		return host;
	}
	
	public MessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}
	
	public void addToWaitingMessages(Message message) {
		messagesWaitingConfirm.put(message.getID(), message);
	}
	
	public void messageConfirmed(int id) {
		messagesWaitingConfirm.remove((Integer) id);
	}
	
	public boolean equals(Connection conn) {
		return conn.getHost().toString().equals(this.getHost().toString()) && conn.getPort() == this.getPort();
	}
}
