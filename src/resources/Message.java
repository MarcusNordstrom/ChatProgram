package resources;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Message Object containing the @User the message is from and a ArrayList of
 * Users to receive the message.
 * 
 * @author UncleBen
 *
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 3894794928698569937L;
	private User user;
	private ArrayList<User> receivers;

	/**
	 * Constructor to form the Message Object
	 * 
	 * @param user
	 *            The User the message is from.
	 * @param receivers
	 *            A list of Users its to.
	 */
	public Message(User user, ArrayList<User> receivers) {
		this.user = user;
		this.receivers = receivers;
	}
	public Message() {}

	/**
	 * Standard get function.
	 * 
	 * @return Instance @User object
	 */
	public User getUser() {
		return user;
	}
	/**
	 * Standard get function
	 * @return Instance @ArrayList with Users
	 */
	public ArrayList<User> getRecivers() {
		return receivers;
	}
}
