package server.messages;

import network.Message;
import system.core.Item;
import system.core.SalesSystem;
import system.core.User;

public class AddItemMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		
		Message message = context.getMessage();

		if (!message.getCommand().equals("AddItem")) {
			return false;
		}		
		
		String[] params = message.getParams();
		
		Item item = new Item(params[0], Integer.parseInt(params[1]));
		User user = context.getUser();
		
		SalesSystem.getInstance().addItemToUser(user, item);
		
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
