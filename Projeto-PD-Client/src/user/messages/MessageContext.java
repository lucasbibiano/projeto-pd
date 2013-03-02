package user.messages;

import system.core.User;
import user.Client;
import network.Message;

public class MessageContext {
	private Message message;
	private User user;
	private Client client;

	public MessageContext(Message message)  {
		setUser(null);
		setMessage(message);
	}
	
	public MessageContext(Message message, User user)  {
		setUser(user);
		setMessage(message);
	}
	
	public MessageContext(Message message, User user, Client client)  {
		setUser(user);
		setMessage(message);
		setClient(client);
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
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
