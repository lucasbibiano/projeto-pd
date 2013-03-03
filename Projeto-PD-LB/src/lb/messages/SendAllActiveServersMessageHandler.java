package lb.messages;

import lb.core.LoadBalancer;
import network.Connection;
import network.Message;

public class SendAllActiveServersMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();
		
		if (!message.getCommand().equals("SendAllActiveServers")) {
			return false;
		}
		
		LoadBalancer lb = context.getLoadBalancer();
		Connection conn = context.getMessage().getConnection();
		
		conn.sendMessage(new Message("Servers", lb.getAllActiveServers()));
		
		return true;
	}

}
