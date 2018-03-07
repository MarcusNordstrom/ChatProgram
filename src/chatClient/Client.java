package chatClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private Controller controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public Client(String ip, int port) throws IOException {
		socket = new Socket(ip,port);
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		new Listener().start();
	}
	
	public void setClientController(Controller controller) {
		this.controller = controller;
	}

	public void send() throws IOException {
		
	}		// ändra



	public void exit() throws IOException {		 // ändra
		if(socket!=null)
		    socket.close();		
	}

	private class Listener extends Thread {
		public void run() {
			String response;
			try {
				while(true) {
					response = ois.readUTF();
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
