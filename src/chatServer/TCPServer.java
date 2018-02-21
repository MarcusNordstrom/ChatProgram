package chatServer;

import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	
	private ServerSocket serverSocket;
	private RunOnThreadN pool;
	
	public TCPServer() {
	}
	
	public void getResponse(String string) {
	}
	
	public class ClientHandler implements Runnable {
		private Socket socket;
		
		public ClientHandler(Socket socket) {
		}
		
		public void run() {
		}
	}
	
	
	
	public class Connection extends Thread {
		
		public void run() {
			
		}
	}
	

}