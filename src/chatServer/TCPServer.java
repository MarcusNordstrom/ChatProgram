package chatServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import resources.User;
import resources.UserMessage;

public class TCPServer {

	private ServerSocket serverSocket;
	private RunOnThreadN pool;
	private Connection connection = new Connection();
	private ArrayList<ClientHandler> ClientList = new ArrayList<ClientHandler>();

	/*
	 * En trådpool instansieras och startas i konstruktorn 
	 */

	public TCPServer(int port, int nbrOfThreads) throws IOException {
		pool = new RunOnThreadN(nbrOfThreads);
		serverSocket = new ServerSocket(port);
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

		public ClientHandler(Socket socket) {
			try {
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				Object obj = objectInputStream.readObject();
				if(obj instanceof User) {
					User readUser = (User)obj;
					this.user = readUser;
				}else if(obj instanceof UserMessage) {
					
				}
					
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Could not read Object.");
			}
		}
		public User getUser() {
			return this.user;
		}
	}



	public class Connection extends Thread {
		/*
		 * Efter att en klient anslutit placeras klienthanteraren i trådpoolens buffert
		 * för att exekveras när tid finns.
		 */
		public void run() {
			System.out.println("Server running, listening to port: " + serverSocket.getLocalPort());
			while(true) {
				try  {
					Socket socket = serverSocket.accept();
					ClientList.add(new ClientHandler(socket));
					for(ClientHandler client : ClientList) {
						pool.execute(client);
					}
//					pool.execute(new ClientHandler(socket));
				} catch(IOException e) { 
					System.err.println(e);
				}
			}
		}
	}
}