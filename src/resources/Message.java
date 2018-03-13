package resources;

import java.io.Serializable;
import java.util.ArrayList;


public class Message implements Serializable{
	private static final long serialVersionUID = 3894794928698569937L;

	private User user;
	private ArrayList<User> recivers;
	
	public Message(User user, ArrayList<User> recivers) {
		this.user = user;
		this.recivers = recivers;
	}

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}

	public ArrayList<User> getRecivers() {
		return recivers;
	}
}
