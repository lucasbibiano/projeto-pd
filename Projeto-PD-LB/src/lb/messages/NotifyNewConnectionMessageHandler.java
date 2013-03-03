package lb.messages;

import lb.core.LoadBalancer;
import network.Message;

public class NotifyNewConnectionMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();
		
		if (!message.getCommand().equals("NotifyNewConnection")) {
			return false;
		}
		
		String[] params = message.getParams();
		
		LoadBalancer lb = context.getLoadBalancer();
		
		lb.newConnectionOnServer(params[0], params[1]);
		
		return true;		
	}

}
