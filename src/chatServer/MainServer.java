package chatServer;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainServer {

	public static void main(String[] args) {
		OfflineWriter ow = new OfflineWriter("files/OfflineMap.txt");
		TCPServer tcpServer = new TCPServer(12345, 50, ow);
		JFrame frame = new JFrame();
		ServerUI sui = new ServerUI();
		frame.setPreferredSize(new Dimension(600, 400));
		frame.add(sui);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
