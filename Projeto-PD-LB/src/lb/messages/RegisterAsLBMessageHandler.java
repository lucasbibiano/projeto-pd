package lb.messages;

import network.Message;

public class RegisterAsLBMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();
		
		if (!message.getCommand().equals("RegisterAsLB")) {
			return false;
		}
		
		String[] params = message.getParams();
				
		if (params[0].equals("senha_secreta")) {
			context.getLoadBalancer().authenticateLoadBalancer(message.getConnection());
		}
		else {
			message.getConnection().sendMessage(new Message("ConnectionRefused", "wrong_password"));
		}
				
		return true;
	}

}
