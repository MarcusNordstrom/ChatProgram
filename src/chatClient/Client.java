package chatClient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

import java.util.Observable;
import resources.SystemMessage;
import resources.User;
import resources.UserList;
import resources.UserMessage;

/**
 * The class creates connection between client and server. 
 * It opens input and output steams to enable communication to server.
 */
public class Client extends Observable {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private UserList ul = null;
	private ServerListener sl;
	private User self;
	private UserList offlineList;
	private UIUsers uiUser;

	/**
	 * Constructor
	 * 
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
	 * Sending test message to server
	 * @param message
	 */
	public void send(String message) {
		System.out.println("Sending test message");
		try {
			oos.writeObject(new User("kent", new ImageIcon()));
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reading from text file all the saved contacts. 
	 */
	public void getOfflineList() {
		try {
			ObjectInputStream fois = new ObjectInputStream(new FileInputStream("files/LocalOfflineMap.txt"));
			offlineList = (UserList) fois.readObject();
			System.out.println(offlineList.getList().toString());
			if(offlineList.getList().size() <= 0) {
				offlineList = null;
				System.out.println("Empty list");
			}else {
				System.out.println(offlineList.toString());
				uiUser.updateOffline(offlineList);
			}
		} catch (IOException | ClassNotFoundException e) {
			offlineList = null;
			System.out.println("Empty list");
		}
	}

	/**
	 * Writing saved users to text file. 
	 * @param ul
	 * @param ol
	 */
	public void setOfflineList(UserList ul, UserList ol) {
		String temp = "";
		String old = "";
		for(User addingFriend : ul.getList()) {
			for(User onlineList : ol.getList()) {
				if(addingFriend.getName().equals(onlineList.getName())) {
					if(!isUserInOfflineList(addingFriend.getName())) {
						offlineList.addUser(addingFriend);
					}
				}
			}
		}
		uiUser.updateOffline(offlineList);
		try {
			ObjectOutputStream foos = new ObjectOutputStream(new FileOutputStream("files/LocalOfflineMap.txt"));
			foos.writeObject(offlineList);
			foos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isUserInOfflineList(String name) {
		for(User friend : offlineList.getList()) {
			if(friend.getName().equals(name))
				return true;
		}
		return false;
	}

	/**
	 * Sends a message to server
	 * @param message
	 */
	public void send(UserMessage message) {
		System.out.println("Sending " + message.getContent() + " to " + message.getReceivers().getUser(0).getName());
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
	public void exit() { 
		if (socket != null)
			try {
				sendDisconnect();
				Thread.sleep(2000);
				socket.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Get for userlist
	 * 
	 * @return UserList a list of users
	 */
	public UserList getList() {
		UserList ret = ul.clone();
		return ret;
	}

	public User getSelf() {
		return self.clone();
	}

	public ImageIcon getImg() {
		return self.getPic();
	}

	/**
	 * Sends the newly created user to the server.
	 * 
	 * @param user
	 *            "user created by the loginUI"
	 */
	public void sendUser(User user) {
		try {
			self = user;
			oos.writeObject(self);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * SubClass that listens to server for messages
	 *
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
					if (response instanceof UserMessage) {
						UserMessage um = (UserMessage) response;
						System.out.println("RECIEVED USERMESSAGE FROM SERVER from: " + um.getUser().getName());
						setChanged();
						notifyObservers(um);

					} else if (response instanceof SystemMessage) {
						SystemMessage sm = (SystemMessage) response;
						if (sm.getPayload() == null) {
						} else {
							if (sm.getPayload() instanceof UserList) {
								System.out.println("New UserList added");
								ul = (UserList) (sm.getPayload());
							}
						}
					} else if (response instanceof UserList) {
						UserList list = (UserList) response;
						ul = list.clone();
						setChanged();
						notifyObservers(ul);
					}
				}
			} catch (IOException e) {

			} catch (ClassNotFoundException classex) {

			}
		}
	}

	public void resend(UserMessage um) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setChanged();
		notifyObservers(um);
	}

	public void addUIUsers(UIUsers uiUsers) {
		this.uiUser = uiUsers;
	}
}