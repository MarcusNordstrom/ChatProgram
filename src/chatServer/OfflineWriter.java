package chatServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import resources.User;
import resources.UserMessage;

public class OfflineWriter extends Thread{
	private String filename;

	public OfflineWriter(String filename) {
		this.filename = filename;
	}

	public void initfilesystem() {
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			HashMap<User, ArrayList<UserMessage>> hm = new HashMap<User, ArrayList<UserMessage>>();
			oos.writeObject(hm);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeMessageToFile(UserMessage msg, User user) {
		ArrayList<UserMessage> messageList;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			Object obj = ois.readObject();
			if(obj instanceof HashMap) {
				HashMap<User, ArrayList<UserMessage>> hm = (HashMap<User, ArrayList<UserMessage>>)obj;
				if(hm.containsKey(user)) {
					messageList = hm.get(user);
				} else {
					messageList = new ArrayList<UserMessage>();
				}
				messageList.add(msg);
				hm.put(user, messageList);
				oos.writeObject(hm);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<UserMessage> getMessages(User user) {
		ArrayList<UserMessage> messageList = null;
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
			Object obj = ois.readObject();
			if(obj instanceof HashMap) {
				HashMap<User, ArrayList<UserMessage>> hm = (HashMap<User, ArrayList<UserMessage>>)obj;
				if(hm.containsKey(user)) {
					messageList = hm.get(user);
					hm.remove(user);
				}
				oos.writeObject(hm);
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

}
