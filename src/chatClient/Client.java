package chatClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;

import resources.User;
import resources.UserList;

public class Client{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private UserList ul = null;
	private ServerListener sl;
	
	public Client(String ip, int port) throws IOException {
		ul = new UserList();
		socket = new Socket(ip,port);

		oos = new ObjectOutputStream(socket.getOutputStream());
		sl = new ServerListener();
		sl.start();
	}

	public void send(String message){//send message to server
		
		System.out.println();
		try {
			oos.writeObject(new User("kent", new ImageIcon()));
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		exit();
	}		

	public void exit(){		 // ändra
		if(socket!=null)
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}
	
	public UserList getList() {
		ul.blankList();
		return ul;		
	}
	
	public void startListener() {
		if(sl.interrupted()) {
			sl = new ServerListener();
			sl.start();
		}
	}
	
	public void stopListener() {
		if(!sl.interrupted()) {
			sl.interrupt();
		}
	}
	
	public void sendUser(String username) {
		
		
	}
		
	private class ServerListener extends Thread {
		public void run() {
			Object response;
			try {
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				while(true) {
					System.out.println("lyssnar");
					response = ois.readObject();
					System.out.println(response.toString());
					System.out.println("mottagit");
					//input from server
				}
			} catch(IOException e) {
				
			} catch (ClassNotFoundException classex) {
				
			}
			exit();
		}
	}



	
}
