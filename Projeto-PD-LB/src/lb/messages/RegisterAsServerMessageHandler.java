package lb.messages;

import network.Message;

public class RegisterAsServerMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();
		
		if (!message.getCommand().equals("RegisterAsServer")) {
			return false;
		}
		
		String[] params = message.getParams();
				
		if (params[0].equals("senha_secreta")) {
			context.getLoadBalancer().authenticateServer(message.getConnection(),
				Integer.parseInt(params[1]), params[2], Integer.parseInt(params[3]));
		}
		else {
			message.getConnection().sendMessage(new Message("ConnectionRefused", "wrong_password"));
		}
		
		return true;
	}

}
