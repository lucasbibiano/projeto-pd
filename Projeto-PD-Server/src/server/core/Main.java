package server.core;

import utils.ConfigManager;

public class Main {
	
	public Main() {

	}
		
	public static void main(String[] args) {
		//ConfigManager.addConfig("server_capacity", Integer.parseInt(args[0]));
		//ConfigManager.addConfig("server_port", Integer.parseInt(args[1]));
		
		ConfigManager.addConfig("server_capacity", 1000);
		ConfigManager.addConfig("server_port", 3334);
		
		Server server = new Server();
		server.start();
	}

}
