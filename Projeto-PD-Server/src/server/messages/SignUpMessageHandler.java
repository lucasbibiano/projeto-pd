package server.messages;

import system.core.SalesSystem;
import network.Message;

public class SignUpMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		//message format
		//id>SignUp?user:encrypted_password
		
		Message message = context.getMessage();
		
		if (!message.getCommand().equals("SignUp")) {
			return false;
		}
		
		String[] params = message.getParams();
		
		SalesSystem.getInstance().createUser(params[0], params[1]);
						
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
