package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import threading.ThreadPoolManager;

public class TCPConnectionManager extends ConnectionManager {

	private ServerSocket socket;
	
	public TCPConnectionManager(ServerSocket socket) throws IOException{
		this.socket = socket;
	}
	
	@Override
	public void listenForConnections() {
		ThreadPoolManager.getInstance().getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						Socket skt = socket.accept();
						Connection conn = new TCPConnection(skt);
						
						getConnectionListener().newConnection(conn);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		});
	}

}
