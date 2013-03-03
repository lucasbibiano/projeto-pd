package lb.messages;

import lb.core.LoadBalancer;
import network.Connection;
import network.Message;

public class NotifyConnectionClosedMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();
		
		if (!message.getCommand().equals("NotifyConnectionClosed")) {
			return false;
		}
		
		String[] params = message.getParams();
		
		LoadBalancer lb = context.getLoadBalancer();
		Connection conn = context.getMessage().getConnection();
		
		lb.closedConnectionOnServer(conn, params[0]);
		
		return true;
	}

}
