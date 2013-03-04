package lb.messages;

import java.util.Arrays;

import network.Message;

public class BroadcastServerMessageMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();
	
		if (!message.getCommand().equals("BroadcastServerMessage")) {
			return false;
		}
		
		String[] params = message.getParams();
		String comm = params[0];
		params = Arrays.copyOfRange(params, 1, params.length);
		
		context.getLoadBalancer().broadcastServerMessage(new Message(comm, params));
		
		return true;
	}

}
