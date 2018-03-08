package chatClient;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class UIUsers extends JPanel implements ActionListener {
	private JLabel lblUsername = new JLabel("");
	private JLabel lblUsersOnline = new JLabel("Users online");
	private JLabel lblUsersOffline = new JLabel("Saved Users");
	private JTextArea taUsersOnline = new JTextArea("");
	private JTextArea taUsersOffline = new JTextArea("");
	private JScrollPane scroll = new JScrollPane();
	private Client client;



	public UIUsers(Client client) {
		this.client = client;
		setLayout(new BorderLayout());
		add(panelTop(), BorderLayout.NORTH);
		add(panelCenter(), BorderLayout.CENTER);
	}


	private JPanel panelTop() {
		JPanel panel = new JPanel(new BorderLayout());
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblUsername, BorderLayout.CENTER);
		return panel;
	}

	private JPanel panelOnline() {
		JPanel panel = new JPanel(new BorderLayout());
		scroll = new JScrollPane(taUsersOnline);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		taUsersOnline.setEditable(false);
		panel.add(lblUsersOnline, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}

	private JPanel panelOffline() {
		JPanel panel = new JPanel(new BorderLayout());
		scroll = new JScrollPane(taUsersOffline);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		taUsersOffline.setEditable(false);
		panel.add(lblUsersOffline, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);

		return panel;
	}

	private JPanel panelCenter() {
		JPanel panel = new JPanel(new GridLayout(2,0));
		panel.add(panelOnline());
		panel.add(panelOffline());		
		return panel;
	}



	public void actionPerformed(ActionEvent e) {
		
	}


//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		JFrame frame = new JFrame("Chat");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
//		frame.setPreferredSize(new Dimension(300,600));
//		frame.add(new UIUsers());
//		frame.pack();
//		frame.setVisible(true);
//	}

}
