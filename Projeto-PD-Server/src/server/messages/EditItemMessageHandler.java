package server.messages;

import network.Message;
import system.core.Item;
import system.core.SalesSystem;
import system.core.User;

public class EditItemMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();

		if (!message.getCommand().equals("EditItem")) {
			return false;
		}		
		
		String[] params = message.getParams();
		
		Item item = new Item(params[1], Integer.parseInt(params[2]));
		User user = context.getUser();
		
		SalesSystem.getInstance().editUserItem(user, params[0], item);
		
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
