package lb.messages;

import lb.core.LoadBalancer;
import network.Message;

public class MessageContext {
	private Message message;
	private LoadBalancer loadBalancer;

	public MessageContext(Message message)  {
		setMessage(message);
	}
	
	public MessageContext(Message message, LoadBalancer loadBalancer)  {
		setMessage(message);
		setLoadBalancer(loadBalancer);
	}
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}

	public LoadBalancer getLoadBalancer() {
		return loadBalancer;
	}

	public void setLoadBalancer(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}
}
