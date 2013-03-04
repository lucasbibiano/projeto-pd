package system.core;

public class Item {
	private String description;
	private int price;
	private User user;
	
	public Item(String desc, int price){
		this.description = desc;
		this.price = price;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public boolean equals(Object another) {
		Item item = (Item) another;
		
		return item.getDescription().equals(getDescription()) &&
			item.getPrice() == getPrice() && item.getUser().equals(getUser());
	}
	
	@Override
	public String toString() {
		return getDescription() + ";" + getPrice() + ";" + getUser().getName();
	}
}
