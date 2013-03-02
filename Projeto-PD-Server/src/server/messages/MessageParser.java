package server.messages;

import java.util.ArrayList;


public class MessageParser {
	
	private static ArrayList<MessageHandler> chain;
	
	static {
		chain = new ArrayList<MessageHandler>();
		
		chain.add(new SignUpMessageHandler());
		chain.add(new LoginMessageHandler());
		chain.add(new AddItemMessageHandler());
		chain.add(new ShowMyItensMessageHandler());
		chain.add(new RemoveItemMessageHandler());
		chain.add(new EditItemMessageHandler());
		chain.add(new ShowItensMessageHandler());
		chain.add(new ShowMyItensMessageHandler());
		chain.add(new SearchItensMessageHandler());
		//...other handlers
	}
	
	public static void parseMessage(MessageContext context){
		for (MessageHandler handler: chain){
			if (handler.handleMessage(context)){
				break;
			}
		}
	}
}
