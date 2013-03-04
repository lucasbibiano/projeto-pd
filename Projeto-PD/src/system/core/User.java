package system.core;

import java.util.ArrayList;
import java.util.Iterator;

import utils.TextAreaLogger;

public class User {
	private String name;
	private String password;
	
	private ArrayList<Item> itensSelling;
	
	public User(String name, String password) {
		setName(name);
		this.password = password;
		itensSelling = new ArrayList<Item>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public Iterator<Item> getItensSelling() {
		return itensSelling.iterator();
	}

	public void addItemToSellingItens(Item item) {
		this.itensSelling.add(item);
		item.setUser(this);
	}
	
	public void removeItemFromSellingItens(String itemDesc) {
		for (int i = 0; i < itensSelling.size(); i++) {
			if (itemDesc.equals(itensSelling.get(i).getDescription())) {
				itensSelling.remove(i);
				
				TextAreaLogger.getInstance().log("Removed item " + itemDesc);

				break;
			}
		}
	}
	
	public int getNumItensSelling() {
		return itensSelling.size();
	}
	
	@Override
	public boolean equals(Object o) {
		User user = (User) o;
		
		return user.getName().equals(this.getName());
	}

	public void editItem(String itemDesc, Item newItem) {
		for (int i = 0; i < itensSelling.size(); i++) {
			if (itemDesc.equals(itensSelling.get(i).getDescription())) {
				itensSelling.get(i).setDescription(newItem.getDescription());
				itensSelling.get(i).setPrice(newItem.getPrice());
				
				TextAreaLogger.getInstance().log("Edited item " + newItem.getDescription());

				break;
			}
		}
	}
	
	@Override
	public String toString() {
		return getName() + ";" + getPassword();
	}
}
