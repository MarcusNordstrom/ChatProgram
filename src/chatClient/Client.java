package chatClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private Controller controller;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public Client(String ip, int port) throws IOException {
		socket = new Socket(ip,port);
		dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		new Listener().start();
	}
	
	public void setClientController(Controller controller) {
		this.controller = controller;
	}

	public void put(String name, String ageStr) throws IOException {
		int age = -1;
		try {
			age = Integer.parseInt(ageStr);
		} catch(NumberFormatException e) {}
		dos.writeUTF("PUT");
		dos.writeUTF(name);
		dos.writeInt(age);
		dos.flush();
	}

	public void get(String name) throws IOException {
		dos.writeUTF("GET");
		dos.writeUTF(name);
		dos.flush();
	}

	public void list() throws IOException {
		dos.writeUTF("LIST");
		dos.flush();
	}

	public void remove(String name) throws IOException {
		dos.writeUTF("REMOVE");
		dos.writeUTF(name);
		dos.flush();
	}

	public void exit() throws IOException {
		if(socket!=null)
		    socket.close();		
	}

	private class Listener extends Thread {
		public void run() {
			String response;
			try {
				while(true) {
					response = dis.readUTF();
					controller.newResponse(response);
				}
			} catch(IOException e) {}
			try {
				exit();
			} catch(IOException e) {}
			controller.newResponse("Klient kopplar ner");
		}
	}
}
