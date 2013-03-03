package lb.messages;


public interface MessageHandler {
	public boolean handleMessage(MessageContext context);
}
