package chatServer;

import java.awt.Component;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;

public class TCPServer{

	private ServerUI sUI;
	private ArrayList<ClientThread> client;
	private ServerSocket sc;
	private int port;
	private boolean continueing;
	private ClientThread clientThread;

	// konstruktor som tar emot en port som inparrameter att lyssna på.
	public TCPServer(int port) {
		this(port, null);
	}

	public TCPServer(int port, ServerUI sUI) {
		//porten
		this.port = port;
		//userinterfacet
		this.sUI = sUI;
		//lista för clienter
		client = new ArrayList<ClientThread>();
	}

	//startar och exekverar tråd
	public void start() {
		try {
			//socket som används
			ServerSocket sc = new ServerSocket(port);
			//väntar tills en anslutning har sket samt blivit accepterad.
			while(continueing) {	
				Socket socket = sc.accept();
				//om continuing = false alltså stoppas
				if(!continueing) {
					break;
				}
				
				//Skapar en klienttråd som har en socket i konstrukotrn, socketen skapad ovan 
				//följer med här...
				ClientThread ct = new ClientThread(socket);
				//lägger till clienttråden i arraylisten med clienttrådar 
				client.add(ct);
				//ct exekveras ( tåden startar)
				ct.start();
			}
			
			//stopas:
			try {
				sc.close();
				//går igenom alla clienter i arraylisten och stänger dess streams samt sockets
				for(int i = 0; i < client.size(); ++i) {
					ClientThread tc = client.get(i);
					try {
						tc.objectIn.close();
						tc.objectOut.close();
						tc.socket.close();
					}

					catch(IOException e) {
						System.out.println("fuck2");
					}
				}
			}catch (IOException e) {
				System.out.println("fuck1");
			}
			
		}catch(IOException e) {
			System.out.println("fuck");
		}
	}
	
	
	protected void stop() {
		continueing = false;
		// connect to myself as Client to exit statement
		// Socket socket = serverSocket.accept();
		try {
			new Socket("localhost", port);
		}
		
		catch(Exception e) {
			System.out.println("fuck3");
		}
	}
	public static void main(String[] args) {
		int port = 1337;	//:D:D:D:D:D::D
		// Skapar ett server objekt och startar det
		ServerUI sui = new ServerUI(port);
		JFrame frame = new JFrame("Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(sui);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(300,300);
		
		TCPServer server = new TCPServer(sui.getPort());
		server.start();
		
	}

	/*
	 * klassen Clienttråd ärver tråd
	 */
	public class ClientThread extends Thread {
		private ObjectInputStream objectIn;
		private ObjectOutputStream objectOut;
		private Socket socket;

		public ClientThread(Socket socket) {
			this.socket = socket;
			try

			{
				//skapr input/output streams 
				objectOut = new ObjectOutputStream(socket.getOutputStream());
				objectIn  = new ObjectInputStream(socket.getInputStream());

			}
			catch (IOException e) {

			}
		}

		public void Close() {
			try {
				if(objectIn != null) {
					objectIn.close();
				}
			}catch(IOException e) {}


			if(objectOut != null) {
				try {
					objectOut.close();
				} catch (IOException e) {}
			}

			if(socket != null) {
				try {
					socket.close();
				} catch (IOException e) {}
			}
		}
	}
}