package lb.core;

import utils.ConfigManager;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConfigManager.addConfig("port", Integer.parseInt(args[0]));
		
		new LoadBalancer();
	}

}
