package resources;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class UserList {
	private ArrayList<User> users = new ArrayList<User>();

	public ArrayList<User> getUser (int[] index) {
		User user;
		ArrayList<User> receivers = new ArrayList<User>();
		for(int i = 0; i <index.length; i++) {
			user = users.get(index[i]);
			receivers.add(user);
		}
		return receivers;
	}
	
	public void blankList() {
		users.add(new User("Cunt", new ImageIcon()));
		users.add(new User("Cyka", new ImageIcon()));
		users.add(new User("Idenahoi", new ImageIcon()));
		users.add(new User("Faggot", new ImageIcon()));
		users.add(new User("Benjiboi", new ImageIcon()));
		users.add(new User("Men Stefan", new ImageIcon()));
	}
	
	public User getUser(int index) {
		return users.get(index);
		
	}
	
	public int size() {
		return users.size();
	}
}
