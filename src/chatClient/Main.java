/**
 * 
 */
package chatClient;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
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
			client = new Client("94.234.170.155",20000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Chat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setPreferredSize(new Dimension(600,400));
		frame.add(new UIChat(client));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame = new JFrame("Log in");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(450,250));
		frame.add(new UILogIn(client , frame));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame = new JFrame("Chat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setPreferredSize(new Dimension(300,600));
		frame.add(new UIUsers(client));
		frame.pack();
		frame.setVisible(true);
	}

}
