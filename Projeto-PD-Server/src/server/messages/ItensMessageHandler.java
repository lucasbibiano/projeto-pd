package server.messages;

import network.Message;
import system.core.Item;
import system.core.SalesSystem;

public class ItensMessageHandler implements MessageHandler {
	
	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();

		if (!message.getCommand().equals("Itens")) {
			return false;
		}		
		
		String[] params = message.getParams();
		
		for (String param: params) {
			String[] split = param.split("\\;");
			
			SalesSystem.getInstance().addItemToUser(SalesSystem.getInstance().getByName(split[2])
				, new Item(split[0], Integer.parseInt(split[1])));
		}
		
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;		
	}
}
