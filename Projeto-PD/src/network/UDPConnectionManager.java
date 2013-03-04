package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Iterator;

import threading.ThreadPoolManager;


public class UDPConnectionManager extends ConnectionManager {

	private DatagramSocket socket;
	
	public UDPConnectionManager(DatagramSocket socket){
		this.socket = socket;
	}

	@Override
	public void listenForConnections() {
		final byte[] buf = new byte[1024];
		
		ThreadPoolManager.getInstance().getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				while (true){
					try {
						DatagramPacket packet = new DatagramPacket(buf, buf.length);
						
						socket.receive(packet);
						
						Message msg = Message.stringToMessage(new String(packet.getData()));
																					
						if (msg.getCommand().equals("Connect")) {
							Connection conn = new UDPConnection(packet.getAddress().getHostAddress(),
								packet.getPort(), 2048);
							addConnection(conn);
							getConnectionListener().newConnection(conn);
						}	
						else if (msg.getCommand().equals("Disconnect")) {
							getConnectionListener().connectionClosed(new UDPConnection(packet.getAddress().getHostAddress(),
								packet.getPort(), 2048));
						}
						else {
							Iterator<Connection> conns = getConnections();
							
							while (conns.hasNext()) {
								Connection conn = conns.next();
								
								if (conn.getHost().toString().equals(packet.getAddress().toString())
										&& conn.getPort() == packet.getPort()) {
									conn.getMessageListener().messageReceived(msg);
									break;
								}
							}							
						}
					} catch (SocketException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}		
				}
			}
			
		});	
	}
}
