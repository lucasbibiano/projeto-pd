package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import threading.ThreadPoolManager;

public class TCPConnection extends Connection {

	private Socket socket;
	
	private BufferedReader in;
	private BufferedWriter out;
	
	public TCPConnection(Socket socket) {
		super(socket.getLocalAddress().getHostAddress(), socket.getPort());
		this.socket = socket; 
	}
	
	@Override
	public void openConnection() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void sendMessage(Message message) {
		try {
			out.write(message.toString());
			out.write("\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void listen() {
		ThreadPoolManager.getInstance().getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				while (true){
					try {
						Message msg = Message.stringToMessage(in.readLine());
						getMessageListener().messageReceived(msg);
					} catch (IOException e) {
						e.printStackTrace();
					}		
				}
			}
			
		});			
	}

	@Override
	public void closeConnection() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
