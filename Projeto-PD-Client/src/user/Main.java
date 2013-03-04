package user;

import java.util.Scanner;

import system.core.Item;
import utils.ConfigManager;

public class Main {
	
	private Scanner in;
	private Client client;
	
	public Main() {
		
		int loadBalancerPort = (Integer) ConfigManager.getConfig("lb_port");
		String loadBalancerAddress = (String) ConfigManager.getConfig("lb_address");
		
		in = new Scanner(System.in);
		client = new Client();
		client.askForServer(loadBalancerAddress, loadBalancerPort);

		while (true) {
			printMenu();
			
			int opt = in.nextInt();
			in.nextLine();
			
			execute(opt);
		}
	}
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) {
		ConfigManager.addConfig("lb_address", args[0]);
		ConfigManager.addConfig("lb_port", Integer.parseInt(args[1]));
		
		ConfigManager.addConfig("protocol", args[2]);
		
		new Main();		
	}
	
	public void printMenu() {
		System.out.println("Escolha uma opção");
		System.out.println("1-Cadastrar usuário");
		System.out.println("2-Realizar login");
		System.out.println("3-Adicionar item");
		System.out.println("4-Editar item");
		System.out.println("5-Remover item");
		System.out.println("6-Exibir meus itens");
		System.out.println("7-Exibir itens de algum usuário");
		System.out.println("8-Pesquisar itens");
		System.out.println("9-Fazer logoff");
		System.out.println("10-Sair");
	}
	
	public void execute(int opt) {
		if (opt == 1) {
			signUp();
		}
		else if (opt == 2) {
			login();
		}
		else if (opt == 3) {
			addItem();
		}
		else if (opt == 4) {
			editItem();
		}
		else if (opt == 5) {
			removeItem();
		}
		else if (opt == 6) {
			showMyItens();
		}
		else if (opt == 7) {
			showUserItens();
		}
		else if (opt == 8) {
			searchItens();
		}
		else if (opt == 9) {
			client.logOff();
		}
		else if (opt == 10) {
			client.logOff();
			client.disconnect();
			in.close();
			System.exit(0);
		}
		else {
			System.out.println("Opção inválida");
		}
	}

	private void searchItens() {
		System.out.println("Digite uma palavra para iniciar a pesquisa:");
		String search = in.nextLine();

		client.searchItens(search);		
	}

	private void showUserItens() {
		System.out.println("Digite o nome do usuário para listar os itens:");
		String user = in.nextLine();

		client.showItens(user);
	}

	private void showMyItens() {
		client.showMyItens();
	}

	private void removeItem() {
		System.out.println("Digite o nome do item a ser removido:");
		String itemDesc = in.nextLine();
		
		client.removeItem(itemDesc);
	}

	private void editItem() {
		System.out.println("Digite o nome do item a ser editado:");
		String itemDesc = in.nextLine();
		
		System.out.println("Digite o novo nome do item a ser editado:");
		String newItemDesc = in.nextLine();
		
		System.out.println("Digite o preço do item");
		int price = in.nextInt();
		in.nextLine();
		
		client.editItem(itemDesc, new Item(newItemDesc, price));	
	}

	private void addItem() {
		System.out.println("Digite o nome do item:");
		String itemDesc = in.nextLine();
		
		System.out.println("Digite o preço do item");
		int price = in.nextInt();
		in.nextLine();
		
		client.addItem(new Item(itemDesc, price));
	}

	private void login() {
		System.out.println("Digite o seu nome de usuário:");
		String name = in.nextLine();
		
		System.out.println("Digite sua senha:");
		String password = in.nextLine();
		
		client.login(name, password);
	}

	private void signUp() {
		System.out.println("Digite o nome de usuário para a conta:");
		String name = in.nextLine();
		
		System.out.println("Digite uma senha para a conta:");
		String password = in.nextLine();
		
		client.signUp(name, password);
	}
}