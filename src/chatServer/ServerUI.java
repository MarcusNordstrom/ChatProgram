package chatServer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerUI extends JPanel implements ActionListener{
	private JButton start = new JButton("Start");
	private JTextArea portEntered = new JTextArea();
	private JTextField enterPort = new JTextField();
	private TCPServer server;
	private int port;

	public ServerUI (int port) {
		setLayout (new BorderLayout());
		add(enterPort, BorderLayout.NORTH);
		add(start, BorderLayout.SOUTH);
		add(portEntered, BorderLayout.CENTER);

		start.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == start) {
			portEntered.setText(enterPort.getText());
			this.port = Integer.parseInt(portEntered.getText());
			server = new TCPServer(port);
			new ServerRunning().start();
		}
	}
	
	public int getPort() {
		return port;
	}

	class ServerRunning extends Thread {

		public void run() {
			server.start();
			server = null;

		}

	}
}
