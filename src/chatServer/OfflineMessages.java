package chatServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

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
	 * @param UserMessage
	 *            object, containing the message to the user.
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
	 * @param The
	 *            name you want to search for in the list.
	 * @return An ArrayList of messages for that User.
	 */
	public ArrayList<UserMessage> receive(String name) {
		ArrayList<UserMessage> returnMessages = new ArrayList<UserMessage>();
		for (Iterator<UserMessage> it = storedMessages.iterator(); it.hasNext();) {
			UserMessage message = it.next();
			for (int i = 0; i < message.getReceivers().size(); i++) {
				if (message.getReceivers().getUser(i).getName().equals(name)) {
					returnMessages.add(message);
					if(message.getReceivers().size()> 1) {
						message.getReceivers().removeUser(message.getReceivers().getUser(i));
						storedMessages.add(message);
					}
					it.remove();
					saveFile();
					return returnMessages;
				}
			}
		}
		if (returnMessages.size() > 0)
			return returnMessages;
		return null;
	}

	/**
	 * Simple boolean that checks if that
	 * 
	 * @param param
	 * @return
	 */
	public boolean checkName(String param) {
		for (UserMessage message : storedMessages) {
			for (int i = 0; i < message.getReceivers().size(); i++) {
				if (message.getReceivers().getUser(i).getName().equals(param))
					return true;
			}
		}
		return false;
	}

	public void initFile() {
		File file = new File(filePath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveFile() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath, false))) {
			oos.writeObject(storedMessages);
			oos.reset();
			oos.flush();
		} catch (IOException e) {
			System.err.println("Now you fucked up");
		}
	}

	public void readFile() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
			storedMessages = (ArrayList<UserMessage>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("The list is empty, nothing to read");
		}
	}

	public String toString() {
		String ret = "";
		for (UserMessage message : storedMessages) {
			ret += message + "\n\n";
		}
		return ret;
	}

}
