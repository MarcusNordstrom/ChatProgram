package resources;

import java.util.ArrayList;

public class Message {
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
