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
				/*
				 * boolean checks that socket is connected and not closed, if true following code is executed
				 */
				while (connected) {
					System.out.println("Client is online");
					Object obj = objectInputStream.readObject();

					/*
					 * If the object received is an instance of the object user - defines user and puts the user and clienthandler in hashmap.
					 */
					if (obj instanceof User) {
						User readUser = (User) obj;
						this.user = readUser;
						// System.out.println(user.getName() + " " + user.getPic().toString());
						OnlineMap.put(user, this);
						
						/*
						 * If the object received is an instance of the object UserMessage - checks what users client wants to send to
						 * and sends the message to those users
						 */
					} else if (obj instanceof UserMessage) {
						UserMessage msg = (UserMessage) obj;
						for (User reciver : msg.getRecivers()) {
							if (OnlineMap.containsKey(reciver)) {
								OnlineMap.get(reciver).send(msg);
							}
						}
						
						/*
						 * If the object received is an instance of the object SystemMessage - checks if payload is = null and if instuction = "DISCONNECT" if true
						 * it closes socket because the client is about to exit.
						 */
					} else if (obj instanceof SystemMessage) {
						SystemMessage smsg = (SystemMessage) obj;
						if (smsg.getPayload() == null) {
							if (smsg.getInstruction().equals("DISCONNECT")) {
								System.out.println("Client Disconnecting");
								socket.close();
								connected = false;
							}
						}
						
						/*
						 * If client is NOT connected it removes the user and ClientHandler from the OnlineMap(HashMap)
						 */
					} if(!connected) {
						OnlineMap.remove(user, this);
						System.out.println("Socket is closed");
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