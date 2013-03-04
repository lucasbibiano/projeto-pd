package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import threading.ThreadPoolManager;

public class TCPConnection extends Connection {

	private Socket socket;
	
	private BufferedReader in;
	private BufferedWriter out;
	
	private boolean listening = false;
	
	public TCPConnection(Socket socket) {
		super(socket.getLocalAddress().getHostAddress(), socket.getPort());
		this.socket = socket; 
		
		try {
			socket.setSoTimeout(300);
		} catch (SocketException e) {
		}
	}
	
	@Override
	public void openConnection() {
		try {
			listening = true;
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
		}		
	}

	@Override
	public void sendMessage(Message message) {
		try {
			out.write(message.toString());
			out.write("\n");
			out.flush();
		} catch (IOException e) {
		}
	}
	
	@Override
	public void listen() {
		ThreadPoolManager.getInstance().getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				while (listening){
					try {
						String inStr = in.readLine();
						
						if (inStr == null)
							continue;
						
						Message msg = Message.stringToMessage(inStr);
						getMessageListener().messageReceived(msg);
					} catch (SocketTimeoutException ste) {
						Thread.yield();
					} catch (IOException e) {
						
					}
				}
			}
			
		});			
	}

	@Override
	public void closeConnection() {
		try {
			listening = false;
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
		}
	}

}
