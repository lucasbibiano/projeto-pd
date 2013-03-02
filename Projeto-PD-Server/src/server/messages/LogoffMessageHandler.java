package server.messages;

public class LogoffMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {

		if (!context.getMessage().getCommand().equals("Logoff")) {
			return false;
		}
		
		context.getServer().logoff(context.getUser());
		
		return true;
	}

}
