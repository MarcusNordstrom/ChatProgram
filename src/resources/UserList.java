package resources;

import java.util.ArrayList;

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
}
