package system.core;

import java.util.ArrayList;
import java.util.Iterator;

import utils.Encrypter;
import utils.TextAreaLogger;
import utils.Tuple;


public class SalesSystem {
	
	private static SalesSystem instance;
	
	private ArrayList<User> users;
	
	private SalesSystem() {	users = new ArrayList<User>(); }
	
	public static SalesSystem getInstance() {
		if (instance == null)
			instance = new SalesSystem();
		
		return instance;
	}
	
	public boolean createUser(String name, String password) {
		if (name.isEmpty()) {
			return false;
		}
		
		User user = new User(name, Encrypter.encrypt(password));

		if (!users.contains(user)) {
			users.add(user);
			
			TextAreaLogger.getInstance().log("Created user " + user.getName());

			return true;
		}		
		
		return false;
	}
	
	public User authenticate(String name, String password) {
		password = Encrypter.encrypt(password);
		
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			
			if (user.getName().equals(name) && password.equals(user.getPassword()))
				return user;
		}
		
		return null;
	}
	
	public Tuple<Iterator<Item>, Integer> searchItensByString(String search) {
		ArrayList<Item> result = new ArrayList<Item>();
		
		for (User user: users) {
			
			Iterator<Item> itens = user.getItensSelling();
			
			while (itens.hasNext()) {
				Item item = itens.next();
				
				if (item.getDescription().contains(search)) {
					result.add(item);
				}
			}
		}
		
		return new Tuple<Iterator<Item>, Integer>(result.iterator(), result.size());
	}

	public User getByName(String name) {
		
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			
			if (user.getName().equals(name))
				return user;
		}
		
		return null;
	}
	
	public Iterator<User> getAllUsers() {
		return users.iterator();
	}
	
	public Iterator<Item> getAllSellingItensByUser(User user) {
		return user.getItensSelling();
	}
	
	public void addItemToUser(User user, Item item) {
		user.addItemToSellingItens(item);
		
		TextAreaLogger.getInstance().log("Added item " + item.getDescription() + " to user " + user.getName());
	}
	
	public void removeItemFromUser(User user, String itemDesc) {
		user.removeItemFromSellingItens(itemDesc);
	}
	
	public void editUserItem(User user, String itemDesc, Item newItem) {
		user.editItem(itemDesc, newItem);
	}
}
