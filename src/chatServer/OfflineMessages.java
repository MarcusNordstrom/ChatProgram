package chatServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import resources.User;
import resources.UserList;
import resources.UserMessage;

/**
 * OfflineMessages is a class that saves messages that could not go forward to a
 * user since its status is offline. Saves it to a .txt file and reads it on
 * initiating the object.
 * 
 * @author UncleBen
 *
 */
public class OfflineMessages {
	private ArrayList<UserMessage> storedMessages = new ArrayList<UserMessage>();
	private String filePath = "files/OfflineMessages.txt";

	/**
	 * Initializes the file incase there is none existing, if it exists read the
	 * file instead to update the ArrayList.
	 */
	public OfflineMessages() {
		initFile();
		readFile();
	}

	/**
	 * Adding a message to the ArrayList and save the current ArrayList to the file.
	 * 
	 * @param param
	 *            UserMessage object, containing the message to the user.
	 */
	public void add(UserMessage param) {
		storedMessages.add(param);
		saveFile();
	}

	/**
	 * Searches through the ArrayList for a message containing a specific user and
	 * if it does, return an ArrayList with the messages for that User and then
	 * remove the messages and save the files
	 * 
	 * @param name
	 *            The name you want to search for in the list.
	 * @return An ArrayList of messages for that User.
	 */
	public ArrayList<UserMessage> receive(String name) {
		ArrayList<UserMessage> returnMessages = new ArrayList<UserMessage>();
		for (Iterator<UserMessage> it = storedMessages.iterator(); it.hasNext();) {
			UserMessage message = it.next();
			if (message.getReceivers().getUser(0).getName().equals(name)) {
				returnMessages.add(message);
				it.remove();
				saveFile();
			}
		}
		if (returnMessages.size() > 0)
			return returnMessages;
		return null;

	}

	/**
	 * Simple boolean that checks if the name you enter have any messages waiting
	 * for them
	 * 
	 * @param param
	 *            The name you want to check.
	 * @return
	 */
	public boolean checkName(String param) {
		for (UserMessage message : storedMessages) {
			if (message.getReceivers().getUser(0).getName().equals(param))
				return true;
		}
		return false;
	}

	/**
	 * Initializes the OfflineMessages.txt file
	 */
	private void initFile() {
		File file = new File(filePath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A function used to call and save the current ArrayList of messages to the
	 * textFile anytime a new one is added or removed.
	 */
	public void saveFile() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath, false))) {
			oos.writeObject(storedMessages);
			oos.reset();
			oos.flush();
		} catch (IOException e) {
			System.err.println("Now you fucked up");
		}
	}

	/**
	 * Reads through the .txt file and sets its content to the instance ArrayList.
	 */
	public void readFile() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
			storedMessages = (ArrayList<UserMessage>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("The list is empty, nothing to read");
		}
	}
	/**
	 * toString method that returns every @UserMessage's toString
	 */
	public String toString() {
		String ret = "";
		for (UserMessage message : storedMessages) {
			ret += message + "\n\n";
		}
		return ret;
	}

}
