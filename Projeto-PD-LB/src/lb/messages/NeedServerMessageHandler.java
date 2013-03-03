package lb.messages;

import lb.core.LoadBalancer;
import network.Connection;
import network.Message;

public class NeedServerMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();
		
		if (!message.getCommand().equals("NeedServer")) {
			return false;
		}
		
		LoadBalancer lb = context.getLoadBalancer();
		Connection conn = context.getMessage().getConnection();
		
		conn.sendMessage(new Message("ConnectTo", lb.suggestServer()));
		
		return true;
	}

}
