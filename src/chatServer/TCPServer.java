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

	/*
	 * En trådpool instansieras och startas i konstruktorn
	 */

	public TCPServer(int port, int nbrOfThreads) {
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
			clients.sendUserList(onlineList);
		}
	}

	/*
	 * Den inre klassen ClientHandler är ingen tråd utan implementerar Runnable.
	 * En tråd i trådpoolen exekverar run-metoden.
	 */
	public class ClientHandler implements Runnable {
		private User user;
		private ObjectInputStream objectInputStream;
		private ObjectOutputStream objectOutputStream;
		private Socket socket;
		private boolean connected;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			System.out.println("Client connected");
			connected = true;
			try {
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			/**
			 * Test av server till client.
			 */
			// try {
			// objectOutputStream.writeObject(user);
			// System.out.println("Skickat");
			// objectOutputStream.flush();
			// } catch (IOException e1) {
			// e1.printStackTrace();
			// }
		}

		public void run() {

			try {
				while (true) {
					if (connected) {
						Object obj = objectInputStream.readObject();
						
						if (obj instanceof User) {
							User readUser = (User) obj;
							user = readUser;
							onlineMap.put(user, this);
							onlineList.addUser(user);
							broadcastUserList();
						} else if (obj instanceof UserMessage) {
							UserMessage msg = (UserMessage) obj;
							for (int i = 0; i < msg.getReceivers().size(); i++) {
								if (onlineMap.containsKey(msg.getReceivers().getUser(i))) {
									sendMessage(new ArrayList<ClientHandler>(onlineMap.values()).get(i).getSocket(),
											msg);
								}
							}
						} else if (obj instanceof SystemMessage) {
							System.out.println("Sys message receve");
							SystemMessage smsg = (SystemMessage) obj;
							if (smsg.getPayload() == null) {
								System.out.println("no payload");
								if (smsg.getInstruction().equals("DISCONNECT")) {
									System.out.println("Client Disconnecting");
									socket.close();
									connected = false;
								}
							}
						}
					} else if (!connected) {
						onlineMap.remove(user, this);
						onlineList.removeUser(user);
						System.out.println("Client disconnected");
						break;
					}
				}

			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Could not read Object.");
			}
		}

		public User getUser() {
			return this.user;
		}

		public Socket getSocket() {
			return this.socket;
		}

		public void sendMessage(Socket socket, UserMessage message) {
			try {
				ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
				send.writeObject(message);
				send.flush();

			} catch (IOException e) {
			}
		}

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
		 * Efter att en klient anslutit placeras klienthanteraren i trådpoolens buffert
		 * för att exekveras när tid finns.
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
	//
	// public class Request extends Thread {
	// private Socket socket;
	// private UserMessage message;
	// private ObjectOutputStream oos;
	//
	// public Request(Socket socket, UserMessage message) {
	// this.socket = socket;
	// this.message = message;
	// }
	//
	// public void run() {
	// System.out.println("Starting Request on " + message.getUser() + "s socket");
	// try {
	// oos = new ObjectOutputStream(socket.getOutputStream());
	// oos.writeObject(this.message);
	// oos.flush();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
}