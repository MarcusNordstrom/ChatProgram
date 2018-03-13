package chatServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import resources.SystemMessage;
import resources.User;
import resources.UserList;
import resources.UserMessage;

public class TCPServer {

	private ServerSocket serverSocket;
	private RunOnThreadN pool;
	private Connection connection = new Connection();
	private ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
	private HashMap<User, ClientHandler> onlineMap = new HashMap<User, ClientHandler>();
	private UserList onlineList = new UserList();
	private OfflineWriter ow;

	/*
	 * Constructor creates new ThreadPoolstart and starts the pool and connection.
	 */

	public TCPServer(int port, int nbrOfThreads, OfflineWriter ow) {
		this.ow = ow;
		pool = new RunOnThreadN(nbrOfThreads);
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.start();
		connection.start();
	}

	public void broadcastUserList() {
		for (ClientHandler clients : onlineMap.values()) {
			clients.sendUserList(this.onlineList.clone());
			System.out.println("Sending UserList to " + clients.getUser().getName());
			System.out.flush();
		}
	}

	public class ClientHandler implements Runnable {
		private User user;
		private ObjectInputStream objectInputStream;
		private ObjectOutputStream objectOutputStream;
		private Socket socket;
		private boolean connected = true;

		/*
		 * Creates ObjectStreams of input/output. writes onlieList to client.
		 */
		public ClientHandler(Socket socket) {
			this.socket = socket;
			System.out.println("Client connected");
			try {
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(onlineList);
				objectOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public synchronized void run() {
			try {
				while (true) {

					/*
					 * checks if socket is connected and if socket is not closed. if true following
					 * code is executed.
					 * 
					 */
					if (connected) {
						Object obj = objectInputStream.readObject();
						/*
						 * If object read is an instance of user: -The object is set to the type user.
						 * -Puts the user and ClientHandler in HashMap-onlienMap. -Puts the user in
						 * UserList - onlineList. -BroadcastUserList is called. -Check for offline
						 * messages
						 */
						if (obj instanceof User) {
							User readUser = (User) obj;
							System.out.println("Recieved User: " + readUser.getName());
							System.out.flush();
							user = readUser;
							onlineMap.put(user, this);
							onlineList.addUser(user);
							broadcastUserList();
							ArrayList<UserMessage> offlineMessageList = ow.getMessages(user);
							if (offlineMessageList != null) {
								for (UserMessage message : offlineMessageList) {
									sendUserMessage(message);
								}
							}
							/*
							 * If object read is an instance of UserMessage: -The object is set to the type
							 * UserMessage.
							 */
						} else if (obj instanceof UserMessage) {
							UserMessage msg = (UserMessage) obj;
							System.out.println("Incomming UserMessage!");
							System.out.println(msg);
							for (User client : msg.getReceivers().getList()) {
								if (onlineMap.containsKey(client)) {
									onlineMap.get(client).sendUserMessage(msg);
									System.out.println("Send message to: " + client.getName());
								}else
									ow.writeMessageToFile(msg, client);
							}

							/*
							 * If object read is an instance of SystemMessage: -The object is set to the
							 * type SystemMessage. -Checks if Payload is null. If true, checks if
							 * instruction is equal to "DISCONNECT". If true,closes socekt and removes user
							 * from onlineList, removes user and ClientHandler from onlineMap.
							 */
						} else if (obj instanceof SystemMessage) {
							System.out.println("Sys message receve");
							System.out.flush();
							SystemMessage smsg = (SystemMessage) obj;
							if (smsg.getPayload() == null) {
								System.out.println("no payload");
								System.out.flush();
								if (smsg.getInstruction().equals("DISCONNECT")) {
									System.out.println("Client Disconnecting");
									System.out.flush();
									socket.close();
									onlineList.removeUser(user);
									onlineMap.remove(user, this);
									clientList.remove(this);
									connected = false;
									broadcastUserList();
								}
							}
						} else
							System.out.println("Not a readable Object");
					}
				}

			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Could not read Object.");
				System.err.println(e.getClass().toString());
				System.err.flush();
				e.printStackTrace();
			}
			System.out.flush();
		}

		/*
		 * Getter for user.
		 */
		public User getUser() {
			return this.user;
		}

		/*
		 * Method used to send message from one client to another.
		 */
		public void sendUserMessage(UserMessage message) {
			try {
				System.out.println("Sending UserMessage to " + user.getName());
				objectOutputStream.writeObject(message);
				objectOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/*
		 * Method used to broadcast UserList when new user is connected so that every
		 * client has an updated onlineList.
		 */
		public void sendUserList(UserList list) {
			try {
				objectOutputStream.writeObject(list);
				objectOutputStream.flush();
			} catch (IOException e) {
			}
		}
	}

	public class Connection extends Thread {

		/*
		 * When client has connected a new ClientHandler is created, This ClientHandler
		 * is also placed in the ThreadPool.
		 */
		public void run() {
			System.out.println("Server running, listening to port: " + serverSocket.getLocalPort() + "\n");
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					clientList.add(new ClientHandler(socket));
					for (ClientHandler client : clientList) {
						pool.execute(client);
					}
					// pool.execute(new ClientHandler(socket));
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
	}
}