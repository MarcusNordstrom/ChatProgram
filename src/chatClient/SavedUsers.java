//package chatClient;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import resources.User;
//
//public class SavedUsers {
//	private ObjectOutputStream oos; 
//	private ObjectInputStream ois;
//	private ArrayList<User> list = new ArrayList<User>();
//
//	/**
//	 * Constructor
//	 * @param filename
//	 * 			a string that indicates which file to use. 
//	 */
//	public SavedUsers(String filename) {
//		try {
//			oos = new ObjectOutputStream(new FileOutputStream(filename));
//			ois = new ObjectInputStream(new FileInputStream(filename));
//		} catch( IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * Writes users to file
//	 * @param user 
//	 * 			the users you want to save. 
//	 * @param username
//	 * 			username of the user that want to save contacts.  
//	 */
//	public void writeUserToFile(User user, String username) {
//		ArrayList<User> userList;
//		try {
//			Object obj = ois.readObject();
//			if(obj instanceof HashMap) {
//				HashMap<String, ArrayList<User>> hm = (HashMap<String, ArrayList<User>>)obj;
//				if(hm.containsKey(username)) {
//					userList = hm.get(username);
//				} else {
//					userList = new ArrayList<User>();
//				} 
//				userList.add(user);
//				hm.put(username, userList);
//				oos.writeObject(hm);
//				oos.flush();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//}