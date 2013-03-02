package server.messages;

import java.util.Iterator;

import system.core.Item;
import system.core.SalesSystem;
import system.core.User;
import network.Message;

public class ShowMyItensMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();

		if (!message.getCommand().equals("ShowMyItens")) {
			return false;
		}		
		
		User user = context.getUser();
		Iterator<Item> itens = SalesSystem.getInstance().getAllSellingItensByUser(user);
		
		message.getConnection().sendMessage(new Message("PrepareReceiveItens", 
			String.valueOf(user.getNumItensSelling())));
		
		while (itens.hasNext()) {
			Item item = itens.next();
			
			message.getConnection().sendMessage(new Message("Item", item.getDescription(),
				String.valueOf(item.getPrice()), item.getUser().getName()));
		}
		
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
