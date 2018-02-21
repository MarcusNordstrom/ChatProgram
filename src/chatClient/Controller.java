package chatClient;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Controller {
	private ClientUI ui = new ClientUI(this);
	private Client client;

	private void showClientUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(client.getClass().getName());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(ui);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

	public Controller(Client client) {
		this.client = client;
		client.setClientController(this);
		showClientUI();
	}

	public void put(String name, String age) {
		try {
			client.put(name, age);
		} catch (IOException e) {
			newResponse(e.toString());
		}
	}

	public void get(String name) {
		try {
			client.get(name);
		} catch (IOException e) {
			newResponse(e.toString());
		}
	}

	public void list() {
		try {
			client.list();
		} catch (IOException e) {
			newResponse(e.toString());
		}
	}

	public void remove(String name) {
		try {
			client.remove(name);
		} catch (IOException e) {
			newResponse(e.toString());
		}
	}

	public void exit() {
		try {
			client.exit();
		} catch (IOException e) {
			newResponse(e.toString());
		}
	}

	public void newResponse(final String response) {
		SwingUtilities.invokeLater(new Runnable() { // beh�vs ej f�r ClientA
			public void run() {
				ui.setResponse(response);
			}
		});
	}

	public static void main(String[] args) {
		try {
			Client clientA = new Client("195.178.227.53", 3440);
			new ClientController(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
