package chatClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import resources.UserList;

public class Client {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private UserList ul = null;
	
	public Client(String ip, int port) throws IOException {
		ul = new UserList();
//		socket = new Socket(ip,port);
//		ois = new ObjectInputStream(socket.getInputStream());
//		oos = new ObjectOutputStream(socket.getOutputStream());
//		new ServerListener().start();
	}

	public void send(String message) throws IOException {//send message to server
		System.out.println(message);
	}		

	public void exit() throws IOException {		 // ändra
		if(socket!=null)
		    socket.close();		
	}
	
	public UserList getList() {
		ul.blankList();
		return ul;		
	}
	
	public void sendUser(String username) {
		
		
	}
		
	private class ServerListener extends Thread {
		public void run() {
			Object response;
			try {
				while(true) {
					response = ois.readObject();
					//input form server
				}
			} catch(IOException e) {
				
			} catch (ClassNotFoundException classex) {
				
			}
			try {
				exit();
			} catch(IOException e) {}
			//Klient kopplar ner
		}
	}



	
}
