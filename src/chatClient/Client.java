package chatClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

import resources.SystemMessage;
import resources.User;
import resources.UserList;
import resources.UserMessage;
/**
 * 
 * @author Sebastian
 *
 */
public class Client {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private UserList ul = null;
	private ServerListener sl;
	private User self;
	/**
	 * Constructor
	 * @param ip
	 * @param port
	 * @throws IOException
	 */
	public Client(String ip, int port) throws IOException {
		ul = new UserList();
		socket = new Socket(ip, port);

		oos = new ObjectOutputStream(socket.getOutputStream());
		sl = new ServerListener();
		sl.start();
	}
	/**
	 * Testmessage to server
	 * @param message
	 */
	public void send(String message) {// send Test User to server
		System.out.println("Sending test message");
		try {
			oos.writeObject(new User("kent", new ImageIcon()));
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends a message to server
	 * @param message
	 */
	public void send(UserMessage message) {
		System.out.println("Sending user message");
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends a disconnect message to the server
	 */
	public void sendDisconnect() {
		System.out.println("Client disconnected");
		try {
			oos.writeObject(new SystemMessage("DISCONNECT"));
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Close the client/socket and disconnect from the server
	 */
	public void exit() { // ändra
		if (socket != null)
			try {
				sendDisconnect();
				socket.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * Get for userlist
	 * @return UserList a list of users
	 */
	public UserList getList() {
		ul.blankList();
		UserList retList = new UserList();
		for(int i = 0; i < ul.size(); i++) {
			retList.
		}
		return ul;
	}
	
	/**
	 * Sends the newly created user to the server.
	 * @param user "user created by the loginUI" 
	 */
	public void sendUser(User user) {
		try {
			oos.writeObject(user);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		self = user;
	}
	/**
	 * SubClass that listens to server for messages
	 * @author Sebastian
	 */
	private class ServerListener extends Thread {
		public void run() {
			Object response;
			try {
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				while (true) {
					System.out.println("lyssnar");
					response = ois.readObject();
					System.out.println(response.toString());
					System.out.println("mottagit");
					if(response instanceof UserMessage) {
						//Read message and render on ui
					}else if(response instanceof SystemMessage) {
						SystemMessage sm = (SystemMessage)response; 
						if(sm.getPayload() == null) {
							//Not sure what kind of instruction the server would send to us???
						}else {
							if(sm.getPayload() instanceof UserList) {
								System.out.println("New UserList added");
								ul = (UserList)(sm.getPayload());
							}
						}
						//Not sure what other messages the server would send
					}
				}
			} catch (IOException e) {

			} catch (ClassNotFoundException classex) {

			}
		}
	}

}
