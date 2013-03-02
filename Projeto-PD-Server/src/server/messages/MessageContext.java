package server.messages;

import server.core.Server;
import system.core.User;
import network.Message;

public class MessageContext {
	private Message message;
	private User user;
	private Server server;

	public MessageContext(Message message)  {
		setUser(null);
		setMessage(message);
	}
	
	public MessageContext(Message message, User user)  {
		setUser(user);
		setMessage(message);
	}
	
	public MessageContext(Message message, User user, Server server)  {
		setUser(user);
		setMessage(message);
		setServer(server);
	}
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
