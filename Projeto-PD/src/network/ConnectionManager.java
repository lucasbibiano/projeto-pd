package network;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class ConnectionManager {
	
	private ConnectionListener connectionListener;
	private ArrayList<Connection> connections;
	
	public ConnectionManager() {
		connections = new ArrayList<Connection>();
	}
	
	public void addConnection(Connection conn) {
		connections.add(conn);
	}
	
	public void removeConnection(Connection conn) {
		connections.remove(conn);
	}
	
	public Iterator<Connection> getConnections() {
		return connections.iterator();
	}
	
	public ConnectionListener getConnectionListener() {
		return connectionListener;
	}

	public void setConnectionListener(ConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}
	
	public abstract void listenForConnections();
}
