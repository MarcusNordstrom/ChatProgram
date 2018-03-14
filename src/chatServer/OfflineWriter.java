package chatServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import resources.User;
import resources.UserList;
import resources.UserMessage;
/**
 * This class writes messages to files if the user is not online
 * 
 *
 */
public class OfflineWriter extends Thread{
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	/**
	 * Constructor
	 * @param filename String that indicates which file to use
	 */
	public OfflineWriter(String filename) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filename));
			 ois = new ObjectInputStream(new FileInputStream(filename));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Method to initialize the hashmap object in the file.
	 */
	public void initfilesystem() {
			try {
				HashMap<User, ArrayList<UserMessage>> hm = new HashMap<User, ArrayList<UserMessage>>();
				oos.writeObject(hm);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * Writes a UserMessage to file
	 * @param msg UserMessage that you want to save
	 * @param user User that you want to save the message to
	 */
	public void writeMessageToFile(UserMessage msg, User user) {
		ArrayList<UserMessage> messageList;
		try{
			Object obj = ois.readObject();
			if(obj instanceof Map) {
				HashMap<User, ArrayList<UserMessage>> hm = (HashMap<User, ArrayList<UserMessage>>)obj;
				if(hm.containsKey(user)) {
					messageList = hm.get(user);
				} else {
					messageList = new ArrayList<UserMessage>();
				}
				messageList.add(msg);
				hm.put(user, messageList);
				oos.writeObject(hm);
				oos.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param user The user that you want all the messages for
	 * @return ArrayList The arraylist that contains all messages sent to the user while offline
	 */
	public ArrayList<UserMessage> getMessages(User user) {
		ArrayList<UserMessage> messageList = null;
		try{
			Object obj = ois.readObject();
			if(obj instanceof Map) {
				HashMap<User, ArrayList<UserMessage>> hm = (HashMap<User, ArrayList<UserMessage>>)obj;
				if(hm.containsKey(user)) {
					messageList = hm.get(user);
					hm.remove(user);
					oos.writeObject(hm);
					oos.flush();
				}	
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return messageList;
	}
	
	/**
	 * Safely closes the streams
	 */
	public void shutDown() {
		try {
			ois.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		OfflineWriter ow = new OfflineWriter("files/OfflineMap.txt");
		ow.initfilesystem();
		User user = new User("Bertil", null);
		User userA[] = {user};
		UserList userlist = new UserList(userA);
		UserMessage usermessage = new UserMessage(user, userlist, "Hej", null);
		ow.writeMessageToFile(usermessage, user);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(ow.getMessages(user));
		ow.shutDown();
	}
}
