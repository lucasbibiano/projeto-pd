package server.messages;

import network.Message;
import system.core.SalesSystem;
import system.core.User;
import utils.TextAreaLogger;

public class RemoveItemMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {

		Message message = context.getMessage();

		if (!message.getCommand().equals("RemoveItem")) {
			return false;
		}		
		
		String[] params = message.getParams();
		
		User user = context.getUser();
		
		TextAreaLogger.getInstance().log(params[0]);
		
		SalesSystem.getInstance().removeItemFromUser(user, params[0]);
		
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;	
	}

}
