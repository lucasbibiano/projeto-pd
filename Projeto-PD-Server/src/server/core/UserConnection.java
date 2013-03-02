package server.core;

import network.Connection;
import network.Message;
import network.MessageListener;
import server.messages.MessageContext;
import server.messages.MessageParser;
import system.core.User;
import utils.TextAreaLogger;

public class UserConnection implements MessageListener {
	private Connection connection;
	private User user;
	private Server server;
	
	public UserConnection(Server server, Connection conn) {
		this.connection = conn;
		this.user = null;
		this.server = server;
		
		connection.setMessageListener(this);
		connection.openConnection();

		connection.listen();
	}	
	
	public UserConnection(Connection conn, User user) {
		this.connection = conn;
		this.user = user;
		
		connection.setMessageListener(this);
		connection.openConnection();

		connection.listen();
	}

	@Override
	public void messageReceived(Message message) {
		message.setConnection(connection);	
		MessageParser.parseMessage(new MessageContext(message, user, server));
		
		TextAreaLogger.getInstance().log(message.toString());
	}
	
	public Connection getConnection() {
		return connection;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void closeConnection() {
		connection.closeConnection();
	}

}
