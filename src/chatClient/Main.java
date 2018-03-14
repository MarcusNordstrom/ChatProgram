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
			//Sebbe 77.53.42.147
			//benji 83.248.13.179
			//Jake 83.254.153.173
<<<<<<< HEAD
			client = new Client("192.168.0.6",12345);
=======
			client = new Client("77.53.42.147",12345);
>>>>>>> master
		} catch (IOException e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Chat");
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
