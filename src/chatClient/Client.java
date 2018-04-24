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
 * 
 * @author Sebastian
 *
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
	 * Testmessage to server
	 * 
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

	public void setOfflineList(UserList ul) {
		String temp = "";
		String old = "";
		System.out.println("Offline 4");
		try {
			if (offlineList == null) {
				offlineList = new UserList();
				System.out.println("Empty OfflineList");
				for(int i = 0; i < ul.getList().size(); i++) {
					offlineList.addUser(ul.getUser(i));
					System.out.println("Added: " + ul.getUser(i) + " to offlineList");
				}
			}else {
				System.out.println("Offline 5");
				for (int i = 0; i < offlineList.getList().size(); i++) {
					for (int j = 0; j < ul.getList().size(); j++) {
						temp = ul.getUser(j).getName();
						old = offlineList.getUser(i).getName();
						System.out.println("Is " + temp + " in offlineList? " + old);
						if (!temp.equals(old)) {
							offlineList.addUser(ul.getUser(j));
							System.out.println("Added: " + ul.getUser(j) + " to offlineList");

						}
					}
				}
			}
			
			uiUser.updateOffline(offlineList);
		} catch (NullPointerException e) {
			System.err.println(e.toString());
		}
		
		try {
			ObjectOutputStream foos = new ObjectOutputStream(new FileOutputStream("files/LocalOfflineMap.txt"));
			foos.writeObject(offlineList);
			foos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sends a message to server
	 * 
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
	public void exit() { // ändra
		if (socket != null)
			try {
				sendDisconnect();
				Thread.sleep(2000);
				socket.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
					if (response instanceof UserMessage) {
						UserMessage um = (UserMessage) response;
						System.out.println("RECIEVED USERMESSAGE FROM SERVER from: " + um.getUser().getName());
						setChanged();
						notifyObservers(um);

					} else if (response instanceof SystemMessage) {
						SystemMessage sm = (SystemMessage) response;
						if (sm.getPayload() == null) {
							// Not sure what kind of instruction the server would send to us???
						} else {
							if (sm.getPayload() instanceof UserList) {
								System.out.println("New UserList added");
								ul = (UserList) (sm.getPayload());
							}
						}
						// Not sure what other messages the server would send
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setChanged();
		notifyObservers(um);

	}

	public void addUIUsers(UIUsers uiUsers) {
		this.uiUser = uiUsers;

	}

}
