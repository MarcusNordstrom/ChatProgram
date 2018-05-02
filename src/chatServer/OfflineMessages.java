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

public class OfflineMessages {
	private ArrayList<UserMessage> storedMessages = new ArrayList<UserMessage>();
	private String filePath = "files/OfflineMessages.txt";

	public OfflineMessages() {
		initFile();
		readFile();
	}
	
	public void add(UserMessage param) {
		storedMessages.add(param);
		saveFile();
	}

	public ArrayList<UserMessage> receive(String name) {
		ArrayList<UserMessage> returnMessages = new ArrayList<UserMessage>();
		for (Iterator<UserMessage> it = storedMessages.iterator(); it.hasNext();) {
			UserMessage message = it.next();
			if(message.getReceivers().getUser(0).getName().equals(name)) {
				returnMessages.add(message);
				it.remove();
				saveFile();
			}
		}
		if (returnMessages.size() > 0)
			return returnMessages;
		return null;
	}

	public boolean checkName(String param) {
		for (UserMessage message : storedMessages) {
			if (message.getReceivers().getUser(0).getName().equals(param))
				return true;
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
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath,false))) {
			oos.writeObject(storedMessages);
			oos.reset();
			oos.flush();
		} catch (IOException e) {
			System.err.println("Now you fucked up");
		}
	}

	public void readFile() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
			storedMessages = (ArrayList<UserMessage>)ois.readObject();
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
