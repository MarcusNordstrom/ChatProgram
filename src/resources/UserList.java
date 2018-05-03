package resources;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * This class is used to save a list of Users for easy access.
 * 
 * @author UncleBen
 *
 */
public class UserList implements Serializable {
	private static final long serialVersionUID = 1342922532128807107L;
	private ArrayList<User> users = new ArrayList<User>();

	/**
	 * Constructor used to take in an array of Users to add them right away.
	 * 
	 * @param users
	 *            Array of @User objects
	 */
	public UserList(User[] users) {
		for (int i = 0; i < users.length; i++) {
			this.users.add(users[i]);
		}
	}

	/**
	 * Default constructor taking in nothing. Used to add and remove Users using
	 * methods
	 */
	public UserList() {
	}

	/**
	 * Clones the current list and returns a new to avoid pointers.
	 */
	public UserList clone() {
		UserList returnList = new UserList();
		for (User currentUser : users) {
			returnList.addUser(currentUser);
		}
		return returnList;
	}

	/**
	 * Standard get function.
	 * 
	 * @param index
	 *            The index of the users you want to receive in the new list.
	 * @return ArrayList
	 */
	public ArrayList<User> getUser(int[] index) {
		User user;
		ArrayList<User> receivers = new ArrayList<User>();
		for (int i = 0; i < index.length; i++) {
			user = users.get(index[i]);
			receivers.add(user);
		}
		return receivers;
	}

	/**
	 * Adding a new User to this List.
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		this.users.add(user);
	}

	/**
	 * Remove the User from this list.
	 * 
	 * @param user
	 */
	public void removeUser(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (this.users.get(i).equals(user)) {
				this.users.remove(i);
			}
		}
	}

	/**
	 * Returns the User at the given index.
	 * 
	 * @param index
	 * @return @User
	 */
	public User getUser(int index) {
		return users.get(index).clone();
	}

	/**
	 * Searches through the list for a given name, if it exists return that User
	 * object.
	 * 
	 * @param name
	 *            The name of the user you want to search for.
	 * @return @User
	 */
	public User searchUser(String name) {
		for (User list : users) {
			if (list.getName().equals(name)) {
				return list;
			}
		}
		return null;
	}

	/**
	 * Standard get function
	 * 
	 * @return an ArrayList of all the users in this object.
	 */
	public ArrayList<User> getList() {
		return this.users;
	}

	/**
	 * @return Returns the size of the ArrayList.
	 */
	public int size() {
		return users.size();
	}

	/**
	 * Basic toString method returning all the names in this list.
	 */
	public String toString() {
		String message = "";
		for (User currentUser : users) {
			message += currentUser.getName() + "\n";
		}
		return message;
	}
}
