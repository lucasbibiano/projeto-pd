package user.messages;

import utils.TextAreaLogger;
import network.Message;

public class ConnectToMessageHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		
		Message message = context.getMessage();

		if (!message.getCommand().equals("ConnectTo")) {
			return false;
		}		
		
		if (message.getParams()[0].equals("none")) {
			return true;
		}
		
		String[] params = message.getParams()[0].split("\\:");
		
		TextAreaLogger.getInstance().log(params[0]);
		TextAreaLogger.getInstance().log(params[1]);

		context.getClient().connect(params[0], Integer.parseInt(params[1]));
		
		return true;
	}

}
