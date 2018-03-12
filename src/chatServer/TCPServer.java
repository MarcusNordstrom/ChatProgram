package chatServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import resources.Message;
import resources.SystemMessage;
import resources.User;
import resources.UserMessage;

public class TCPServer {

	private ServerSocket serverSocket;
	private RunOnThreadN pool;
	private Connection connection = new Connection();
	private ArrayList<ClientHandler> ClientList = new ArrayList<ClientHandler>();
	private HashMap<User, ClientHandler> OnlineMap = new HashMap<User, ClientHandler>();

	public TCPServer(int port, int nbrOfThreads) {
		pool = new RunOnThreadN(nbrOfThreads);
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
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
		private boolean connected = socket.isConnected() && !socket.isClosed();
		
		/*
		 * constructor, creates objectstreams of input/output streams.
		 */
		public ClientHandler(Socket socket) {
			this.socket = socket;
			System.out.println("Client connected");
			try {
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
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
				e.getStackTrace();
			}
		}

		public User getUser() {
			return this.user;
		}

		public void send(Message msg) {

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
		 * Listens for connection, if connected creates new ClientHandler.
		 * ClientHandler is placed in threadpools buffer and is executed when available.
		 */
		public void run() {
			System.out.println("Server running, listening to port: " + serverSocket.getLocalPort() + "\n");
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					ClientList.add(new ClientHandler(socket));
					for (ClientHandler client : ClientList) {
						pool.execute(client);
					}
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
	}
}