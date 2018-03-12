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

	/*
	 * Den inre klassen ClientHandler är ingen tråd utan implementerar Runnable.
	 * En tråd i trådpoolen exekverar run-metoden.
	 */

	public class ClientHandler implements Runnable {
		private User user;
		private ObjectInputStream objectInputStream;
		private ObjectOutputStream objectOutputStream;
		private Socket socket;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			System.out.println("Client connected");
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
					if (socket.isConnected()) {
						System.out.println("Client is online");
						Object obj = objectInputStream.readObject();
						if (obj instanceof User) {
							User readUser = (User) obj;
							this.user = readUser;
							// System.out.println(user.getName() + " " + user.getPic().toString());
							OnlineMap.put(user, this);
						} else if (obj instanceof UserMessage) {
							UserMessage msg = (UserMessage) obj;
							for (User reciver : msg.getRecivers()) {
								if (OnlineMap.containsKey(reciver)) {
									OnlineMap.get(reciver).send(msg);
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
									OnlineMap.remove(user, this);
									System.out.println("Client dissconnected");
									break;
								}
							}
							System.out.println("done");
						}
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
	}

	public class Connection extends Thread {
		/*
		 * Efter att en klient anslutit placeras klienthanteraren i trådpoolens
		 * buffert för att exekveras när tid finns.
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
					// pool.execute(new ClientHandler(socket));
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
	}
}