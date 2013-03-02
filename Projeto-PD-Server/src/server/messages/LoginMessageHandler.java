package server.messages;

import system.core.SalesSystem;
import system.core.User;
import network.Message;

public class LoginMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		//message format
		//id>Login?user:encrypted_password
		
		Message message = context.getMessage();
		
		if (!message.getCommand().equals("Login")) {
			return false;
		}
		
		String[] params = message.getParams();
		
		User user = SalesSystem.getInstance().authenticate(params[0], params[1]);
		
		if (user == null) {
			message.getConnection().sendMessage(new Message("FAIL", String.valueOf(message.getID())));
			return true;
		}
		
		context.getServer().authenticateConnection(message.getConnection(), user);
		
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
