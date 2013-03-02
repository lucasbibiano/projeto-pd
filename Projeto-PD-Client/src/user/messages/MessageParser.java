package user.messages;

import java.util.ArrayList;

import user.messages.MessageContext;

public class MessageParser {
	
	private static ArrayList<MessageHandler> chain;
	
	static {
		chain = new ArrayList<MessageHandler>();
		
		chain.add(new ItemMessageHandler());
		chain.add(new PrepareReceiveItensHandler());
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
