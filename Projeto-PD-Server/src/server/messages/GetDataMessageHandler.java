package server.messages;

import java.util.ArrayList;
import java.util.Iterator;

import system.core.Item;
import system.core.SalesSystem;
import system.core.User;

import network.Message;

public class GetDataMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();

		if (!message.getCommand().equals("GetData")) {
			return false;
		}
		
		Iterator<User> users = SalesSystem.getInstance().getAllUsers();
		ArrayList<String> result = new ArrayList<String>();
	
		for (User user = users.next(); users.hasNext();) {
			result.add(user.getName() + ";" + user.getPassword());
		}
		
		message.getConnection().sendMessage(new Message("Users", result.toArray(new String[result.size()])));
		
		users = SalesSystem.getInstance().getAllUsers();
		
		result.clear();

		for (User user = users.next(); users.hasNext();) {
			Iterator<Item> itens = user.getItensSelling();
			
			for (Item item = itens.next(); itens.hasNext();) {
				result.add(item.getDescription() + ";" + item.getPrice() + ";" + item.getUser().getName());
			}
		}
		
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
