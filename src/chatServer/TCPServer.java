package chatServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	private ServerSocket serverSocket;
	private RunOnThreadN pool;
	private Connection connection = new Connection();

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
					pool.execute(new ClientHandler(socket));
				} catch(IOException e) { 
					System.err.println(e);
				}
			}
		}
	}
}