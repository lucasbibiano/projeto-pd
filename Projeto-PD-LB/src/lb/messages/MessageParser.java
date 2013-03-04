package lb.messages;

import java.util.ArrayList;

public class MessageParser {
	
	private static ArrayList<MessageHandler> chain;
	
	static {
		chain = new ArrayList<MessageHandler>();
		chain.add(new NeedServerMessageHandler());
		chain.add(new RegisterAsServerMessageHandler());
		chain.add(new RegisterAsLBMessageHandler());
		chain.add(new NotifyConnectionClosedMessageHandler());
		chain.add(new NotifyNewConnectionMessageHandler());
		chain.add(new SendAllActiveServersMessageHandler());
		chain.add(new BroadcastServerMessageMessageHandler());
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