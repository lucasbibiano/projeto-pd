package user;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import network.Connection;
import network.Message;
import network.MessageListener;
import network.TCPConnection;
import user.messages.MessageContext;
import system.core.Item;
import user.messages.MessageParser;
import utils.Encrypter;
import utils.TextAreaLogger;

public class Client implements MessageListener {
	private Connection serverConnection;
	
	private ArrayList<Integer> waitingConfirmPackets;
		
	
	public Client() {
		TextAreaLogger.getInstance().log("Starting client...");
		waitingConfirmPackets = new ArrayList<Integer>();
	}
	
	public void connect(String host, int port) {
		//TODO: mudar aqui pra impl certa
		
		try {
			serverConnection = new TCPConnection(new Socket(host, port));
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
		Message msg = new Message("SignUp", user, Encrypter.encrypt(password));
		
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
		TextAreaLogger.getInstance().log("========== Minha loja ==========");
		TextAreaLogger.getInstance().log("Descrição\t\t\tPreço\tAnunciante");

		serverConnection.sendMessage(new Message("ShowMyItens"));
		
		//pra dar tempo dos itens chegaram, gambiarra, mas o que eu posso fazer?
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void showItens(String user) {
		TextAreaLogger.getInstance().log("========== Loja " + user + " ==========");
		TextAreaLogger.getInstance().log("Descrição\t\t\tPreço\tAnunciante");
		serverConnection.sendMessage(new Message("ShowItens", user));
		
		//pra dar tempo dos itens chegaram, gambiarra, mas o que eu posso fazer?
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void searchItens(String search) {
		TextAreaLogger.getInstance().log("========== Resultados da pesquisa ==========");
		TextAreaLogger.getInstance().log("Descrição\t\t\tPreço\tAnunciante");
		
		serverConnection.sendMessage(new Message("SearchItens", search));
		
		//pra dar tempo dos itens chegaram, gambiarra, mas o que eu posso fazer?
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void waitItens(int numItens) {
	
	}
	
	public void waitConfirm(int id) {
		waitingConfirmPackets.add(id);
	}
	
	@Override
	public void messageReceived(Message message) {
		message.setConnection(serverConnection);
		MessageParser.parseMessage(new MessageContext(message, null, this));
	}
}