package chatServer;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

public class MainServer {

	public static void main(String[] args) {
		OfflineWriter ow = new OfflineWriter("files/OfflineMap.txt");
		ServerUI sui = new ServerUI();
		TCPServer tcpServer = new TCPServer(12345, 50, ow , sui);
		JFrame frame = new JFrame();
		
		frame.setPreferredSize(new Dimension(600, 400));
		frame.add(sui);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
}
