package resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.DefaultBoundedRangeModel;

public class SaveContacts {
	private String folderpath = "savedContacts/";
	private String format = ".data";
	private HashMap<User, File> userHashMap;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public SaveContacts() {
		userHashMap = new HashMap<User, File>();
	}
	public void save(User from, User to) {
		if (userExists(from)) {
			try {
				ois = new ObjectInputStream(new FileInputStream(getFile(from)));
				oos = new ObjectOutputStream(new FileOutputStream(getFile(from)));
				UserList list = (UserList) ois.readObject();
				list.addUser(to);
				oos.writeObject(list);
				oos.flush();
				oos.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if (!userExists(from)) {
			try {
				File file = new File(folderpath + from.getName() + format);
				FileOutputStream fos = new FileOutputStream(file);
				oos = new ObjectOutputStream(fos);
				UserList list = new UserList();
				list.addUser(to);
				userHashMap.put(from, file);
				oos.writeObject(list);
				oos.flush();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public UserList get(User from) {
		UserList returnList = new UserList();
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(getFile(from)))){
				returnList = (UserList)ois.readObject();
				ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public boolean userExists(User from) {
		return userHashMap.containsKey(from);
	}

	public File getFile(User from) {
		return userHashMap.get(from);
	}
	public String toString() {
		return userHashMap.toString();
	}
	

}
