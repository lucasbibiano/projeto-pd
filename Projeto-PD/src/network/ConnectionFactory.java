package network;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import utils.ConfigManager;

public class ConnectionFactory {
	public static Connection getConnectionImplByConfig(String host, int port) throws UnknownHostException, IOException {
		String implProtocol = (String) ConfigManager.getConfig("protocol"); 
		
		if (implProtocol.equals("udp")) {
			return new UDPConnection(host, port, 2048);
		}
		
		return new TCPConnection(new Socket(host, port));		
	}
	
	public static ConnectionManager getConnectionManagerImplByConfig(int port) throws IOException {
		String implProtocol = (String) ConfigManager.getConfig("protocol"); 
		
		if (implProtocol.equals("udp")) {
			return new UDPConnectionManager(new DatagramSocket(port));
		}
		
		return new TCPConnectionManager(new ServerSocket(port));
	}
}
