/**
 * 
 */
package chatClient;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.UIManager;

/**
 * @author Sebastian Carlsson
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = null;
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			client = new Client("77.53.42.147",12345);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Chat");
//		frame.setPreferredSize(new Dimension(600,400));
//		frame.add(new UIChat(client));
//		frame.pack();
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
		
		frame = new JFrame("Log in");
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(450,250));
		frame.add(new UILogIn(client , frame));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame = new JFrame("Chat");
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		frame.setPreferredSize(new Dimension(400,600));
		frame.add(new UIUsers(client, frame));
		frame.pack();
		frame.setVisible(true);
	}

}
