package server.messages;

import java.util.Iterator;

import network.Message;
import system.core.Item;
import system.core.SalesSystem;
import utils.Tuple;

public class SearchItensMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();

		if (!message.getCommand().equals("SearchItens")) {
			return false;
		}		
		
		String[] params = message.getParams();
		
		Tuple<Iterator<Item>, Integer> searchResult = 
				SalesSystem.getInstance().searchItensByString(params[0]);
		
		Iterator<Item> itens = searchResult.a;
		
		message.getConnection().sendMessage(new Message("PrepareReceiveItens", 
			String.valueOf(searchResult.b)));
		
		while (itens.hasNext()) {
			Item item = itens.next();
			
			message.getConnection().sendMessage(new Message("Item", item.getDescription(),
				String.valueOf(item.getPrice()), item.getUser().getName()));
		}
		
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
