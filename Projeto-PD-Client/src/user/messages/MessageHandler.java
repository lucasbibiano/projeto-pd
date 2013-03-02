package user.messages;

import user.messages.MessageContext;

public interface MessageHandler {
	public boolean handleMessage(MessageContext context);
}
