package user.messages;

import network.Message;
import user.messages.MessageContext;
import user.messages.MessageHandler;

public class PrepareReceiveItensHandler implements MessageHandler {

	@Override
	public boolean handleMessage(MessageContext context) {
		Message message = context.getMessage();

		if (!message.getCommand().equals("PrepareReceiveItens")) {
			return false;
		}		
		
		String[] params = message.getParams();
		
		context.getClient().waitItens(Integer.parseInt(params[0]));
								
		message.getConnection().sendMessage(new Message("OK", String.valueOf(message.getID())));
		
		return true;
	}

}
