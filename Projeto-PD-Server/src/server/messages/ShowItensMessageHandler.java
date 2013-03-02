package server.messages;

import java.util.Iterator;

import network.Message;
import system.core.Item;
import system.core.SalesSystem;
import system.core.User;

public class ShowItensMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();

		if (!message.getCommand().equals("ShowItens")) {
			return false;
		}		
		
		String[] params = message.getParams();
		
		User user = SalesSystem.getInstance().getByName(params[0]);
		
		if (user == null) {
			return true;
		}
		
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
