package user.messages;

import network.Message;
import user.messages.MessageHandler;
import user.messages.MessageContext;
import system.core.Item;
import utils.TextAreaLogger;

public class ItemMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		
		Message message = context.getMessage();

		if (!message.getCommand().equals("Item")) {
			return false;
		}		
		
		String[] params = message.getParams();
		
		Item item = new Item(params[0], Integer.parseInt(params[1]));	
		
		TextAreaLogger.getInstance().log(item.getDescription() + "\t\t\t$" + item.getPrice() + "\t" +
			params[2]);
				
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
