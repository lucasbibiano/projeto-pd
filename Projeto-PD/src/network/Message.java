package network;

import java.io.Serializable;

import utils.TextAreaLogger;

@SuppressWarnings("serial")
public class Message implements Serializable{
	
	private String command;
	private String[] params;
	private int ID;
	
	private Connection connection;
	
	protected static int nextID = 0;
	
	public Message(String command, String...params){
		this.command = command;
		this.params = params;
		this.ID = nextID;
		
		nextID++;
	}
	
	private Message(int id, String command, String...params) {
		this(command, params);
		this.ID = id;
	}

	@Override
	public String toString(){
		String result = String.valueOf(ID) + ">" + command + "?";
		
		for (String s: params){
			result += s + "|";
		}
		
		if (params.length == 0) {
			result += "|";
		}
				
		return result;		
	}
	
	public static Message stringToMessage(String string){
		String[] split = string.split("\\>");
						
		int id = Integer.parseInt(split[0]);
		string = split[1];
		
		split = string.split("\\?");
		
		String command = split[0];
		String[] params = split[1].split("\\|");
		
		return new Message(id, command, params);
	}
	
	public String getCommand() {
		return command;
	}

	public String[] getParams(){
		return params;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
