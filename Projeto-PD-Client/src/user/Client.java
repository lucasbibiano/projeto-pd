package user;

import java.io.IOException;
import java.net.UnknownHostException;

import network.Connection;
import network.ConnectionFactory;
import network.Message;
import network.MessageListener;
import threading.ThreadPoolManager;
import user.messages.MessageContext;
import system.core.Item;
import user.messages.MessageParser;
import utils.Encrypter;

public class Client implements MessageListener {
	private Connection serverConnection;
	
	
	private ServerAsk ask;
	
	public Client() {
		System.out.println("Inciando cliente...");
	}
	
	public void askForServer(String hostLB, int port) {
		ask = new ServerAsk(this, hostLB, port);

		ThreadPoolManager.getInstance().getExecutorService().execute(ask);
	}
		
	public void connect(String host, int port) {
		//TODO: mudar aqui pra impl certa
				
		if (ask.gotServer)
			return;
		
		System.out.println("Servidor encontrado");
		
		ask.gotServer = true;
		
		try {
			serverConnection = ConnectionFactory.getConnectionImplByConfig(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		serverConnection.openConnection();
		serverConnection.setMessageListener(this);
		serverConnection.listen();
	}
	
	public void signUp(String user, String password) {
		Message msg = new Message("SignUp", user, password);
		
		serverConnection.sendMessage(msg);
	}
	
	public void login(String user, String password) {
		Message msg = new Message("Login", user, Encrypter.encrypt(password));
		
		serverConnection.sendMessage(msg);
	}
	
	public void addItem(Item item) {
		Message msg = new Message("AddItem", item.getDescription(), String.valueOf(item.getPrice()));

		serverConnection.sendMessage(msg);
	}
	
	public void removeItem(String itemDesc) {
		Message msg = new Message("RemoveItem", itemDesc);

		serverConnection.sendMessage(msg);
	}
	
	public void editItem(String itemDesc, Item newItem) {
		Message msg = new Message("EditItem", itemDesc, newItem.getDescription(),
			String.valueOf(newItem.getPrice()));
		
		serverConnection.sendMessage(msg);
	}

	public void logOff() {
		serverConnection.sendMessage(new Message("Logoff"));
	}
	
	public void disconnect() {
		serverConnection.closeConnection();
	}
	
	public void showMyItens() {
		System.out.println("========== Minha loja ==========");
		System.out.printf("%-32s%-10s%-16s%n", "Descrição", "Preço", "Anunciante");

		serverConnection.sendMessage(new Message("ShowMyItens"));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void showItens(String user) {
		System.out.println("========== Loja " + user + " ==========");
		System.out.printf("%-32s$%-10s%-16s%n", "Descrição", "Preço", "Anunciante");
		serverConnection.sendMessage(new Message("ShowItens", user));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void searchItens(String search) {
		System.out.println("========== Resultados da pesquisa ==========");
		System.out.printf("%-32s$%-10s%-16s%n", "Descrição", "Preço", "Anunciante");
		
		serverConnection.sendMessage(new Message("SearchItens", search));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void waitItens(int numItens) {
	
	}

	
	@Override
	public void messageReceived(Message message) {
		message.setConnection(serverConnection);
		MessageParser.parseMessage(new MessageContext(message, null, this));
	}
	
	private class ServerAsk implements Runnable, MessageListener {

		private String hostLB;
		private int port;
		private volatile boolean gotServer;
		private Client client;
		private Connection loadBalancerConn = null;
		
		public ServerAsk(Client client, String hostLB, int port) {
			this.hostLB = hostLB;
			this.port = port;
			this.client = client;
		}

		@Override
		public void messageReceived(Message message) {
			message.setConnection(loadBalancerConn);
			MessageParser.parseMessage(new MessageContext(message, null, client));
		}

		@Override
		public void run() {

			while (loadBalancerConn == null) {
				try {
					loadBalancerConn = ConnectionFactory.getConnectionImplByConfig(hostLB, port);
				} catch (Exception e) {
					Thread.yield();
					System.out.println("Tentando novamente...");
					continue;
				}
			}
			
			loadBalancerConn.openConnection();
			loadBalancerConn.setMessageListener(this);
			loadBalancerConn.listen();
			
			while (!gotServer) {
				loadBalancerConn.sendMessage(new Message("NeedServer"));
				System.out.println("Procurando servidor...");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}		
			
			loadBalancerConn.closeConnection();
		}
		
	}
}