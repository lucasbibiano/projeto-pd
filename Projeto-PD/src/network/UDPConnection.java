package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import threading.ThreadPoolManager;

public class UDPConnection extends Connection {

	private byte[] buf;
	private DatagramSocket socket;
	private boolean listening;
	private int maxBufSize;
	
	public UDPConnection(String host, int port, int maxBufSize) {
		super(host, port);
		this.maxBufSize = maxBufSize;
		buf = new byte[maxBufSize];
		
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(300);
		} catch (SocketException e) {
		}
	}
	
	@Override
	public void sendMessage(Message message) {		
		buf = new byte[maxBufSize];
		DatagramPacket packet = new DatagramPacket(buf, buf.length, getHost(), getPort());
		
		Arrays.fill(buf, (byte) 0);
		
		int i = 0;
		for (byte b: message.toString().getBytes()) {
			buf[i] = b;
			i++;
		}
		
		
		packet.setData(buf, 0, buf.length);
		
		try {
			socket.send(packet);
		} catch (IOException e) {
		}
	}

	@Override
	public void openConnection() {
		DatagramPacket packet = new DatagramPacket(buf, buf.length, getHost(), getPort());
		Message message = new Message("Connect");

		packet.setData(message.toString().getBytes());
		
		addToWaitingMessages(message);
		
		listening = true;
		
		try {
			socket.send(packet);
		} catch (IOException e) {
		}		
	}

	@Override
	public void closeConnection() {
		DatagramPacket packet = new DatagramPacket(buf, buf.length, getHost(), getPort());
		Message message = new Message("Disconnect");

		packet.setData(message.toString().getBytes());
		listening = false;
				
		try {
			socket.send(packet);
		} catch (IOException e) {
		}	
		
		socket.close();
	}

	@Override
	public void listen() {
		ThreadPoolManager.getInstance().getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				while (listening){
					try {
						if (socket.isClosed())
							continue;
						
						DatagramPacket packet = new DatagramPacket(buf, buf.length);
						
						try {
							socket.receive(packet);
						} catch (SocketTimeoutException e) {
							continue;
						}
																		
						Message msg = Message.stringToMessage(new String(packet.getData()));
												
						getMessageListener().messageReceived(msg);
					} catch (SocketException e) {
					} catch (IOException e) {
					}		
				}
			}
			
		});	
	}
}