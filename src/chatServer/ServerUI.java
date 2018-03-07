package chatServer;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import chatClient.UIChat;

public class ServerUI extends JPanel {

	private JTextArea jta = new JTextArea("Server");

	public ServerUI() {
		JPanel jp = new JPanel();
		jp.add(jta);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setPreferredSize(new Dimension(600,400));
		frame.add(new ServerUI());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}