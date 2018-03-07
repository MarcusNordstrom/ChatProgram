package chatClient;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class UIUsers extends JPanel implements ActionListener {
	private JLabel lblUsername = new JLabel("ABRONDIN");
	private JButton btnLogOut = new JButton("Log out");
	private JLabel lblUsersOnline = new JLabel("Users online");
	private JLabel lblUsersOffline = new JLabel("Users offline");
	private JTextArea taUsersOnline = new JTextArea("Anna är online");
	private JTextArea taUsersOffline = new JTextArea("Anna är offline");
	private JScrollPane scroll = new JScrollPane();
	private Client client;



	public UIUsers(Client client) {
		this.client = client;
		setLayout(new BorderLayout());
		add(panelTop(), BorderLayout.NORTH);
		add(panelCenter(), BorderLayout.CENTER);
		btnLogOut.addActionListener(this);
	}


	private JPanel panelTop() {
		JPanel panel = new JPanel(new BorderLayout());
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblUsername, BorderLayout.CENTER);
		panel.add(btnLogOut, BorderLayout.EAST);
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
		if(e.getSource() == btnLogOut) {
			// DISCONNECT . ÖPPNA LOGGA IN FÖNSTER
		}
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
