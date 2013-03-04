package server.messages;

import network.Message;

public class GetDataMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();

		if (!message.getCommand().equals("GetData")) {
			return false;
		}
		
		context.getServer().sendData();
		
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
