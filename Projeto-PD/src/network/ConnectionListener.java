package network;

public interface ConnectionListener {
	public void newConnection(Connection connection);
	public void connectionClosed(Connection connection);
}
