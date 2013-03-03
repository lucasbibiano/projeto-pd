package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import threading.ThreadPoolManager;

public class UDPConnection extends Connection {

	private byte[] buf;
	private DatagramSocket socket;
	
	public UDPConnection(String host, int port, int maxBufSize) {
		super(host, port);
		buf = new byte[maxBufSize];
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendMessage(Message message) {		
		DatagramPacket packet = new DatagramPacket(buf, buf.length, getHost(), getPort());
		
		packet.setData(message.toString().getBytes());
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void openConnection() {
		DatagramPacket packet = new DatagramPacket(buf, buf.length, getHost(), getPort());
		Message message = new Message("Connect");

		packet.setData(message.toString().getBytes());
		
		addToWaitingMessages(message);
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void closeConnection() {
		DatagramPacket packet = new DatagramPacket(buf, buf.length, getHost(), getPort());
		Message message = new Message("Disconnect");

		packet.setData(message.toString().getBytes());
		
		addToWaitingMessages(message);
		
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		socket.close();
	}

	@Override
	public void listen() {
		ThreadPoolManager.getInstance().getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				while (true){
					try {
						DatagramPacket packet = new DatagramPacket(buf, buf.length);
						
						socket.receive(packet);
																		
						Message msg = Message.stringToMessage(new String(packet.getData()));
												
						getMessageListener().messageReceived(msg);
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