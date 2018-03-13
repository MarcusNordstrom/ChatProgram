package resources;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class UserList implements Serializable {
	private static final long serialVersionUID = 1342922532128807107L;
	private ArrayList<User> users = new ArrayList<User>();
	
	public UserList(User[] users) {
		for(int i = 0; i < users.length; i++) {
			this.users.add(users[i]);
		}
	}
	public UserList() {
		
	}
	public UserList clone() {
		UserList returnList = new UserList();
		for(User currentUser : users) {
			returnList.addUser(currentUser);
		}
		return returnList;
	}
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
	public void addUser(User user) {
		this.users.add(user);
	}
	public void removeUser(User user) {
		for(int i = 0; i < users.size(); i++) {
			if(this.users.get(i).equals(user)) {
				this.users.remove(i);
			}
		}
	}
	public User getUser(int index) {
		return users.get(index).clone();
	}
	public User searchUser(String name) {
		for(User list : users) {
			if(list.getName().equals(name)) {
				return list;
			}
		}
		return null;
	}
	public ArrayList<User> getList(){
		return this.users;
	}
	public int size() {
		return users.size();
	}
	public String toString() {
		String message = "";
		for(User currentUser : users) {
			message += currentUser.getName() + "\n";
		}
		return message;
	}
}
