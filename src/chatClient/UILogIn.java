package chatClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import resources.UserList;

public class UILogIn extends JPanel implements ActionListener {
	private JLabel lblHeadline = new JLabel("Welcome to log in");
	private JLabel lblUsername = new JLabel("Username: ");
	private JTextField tfUsername = new JTextField();
	private JButton btnLogIn = new JButton("Log In");
	private Client client;
	private UserList ul = new UserList();
	JFrame frame;


	public UILogIn(Client client, JFrame frame) {
		this.client = client;
		this.frame = frame;
		setLayout(new BorderLayout());
		add(panelCenter(), BorderLayout.CENTER);
		btnLogIn.addActionListener(this);
	}

	private JPanel panelCenter() {
		JPanel panel = new JPanel(new GridLayout(3,0));
		panel.add(panelTop());
		panel.add(btnLogIn);
		return panel;
	}

	private JPanel panelTop() {
		JPanel panel = new JPanel(new GridLayout(2,0));
		lblHeadline.setFont(new Font("Comic sans", Font.BOLD, 14));
		lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblHeadline);
		panel.add(panelUsername());
		return panel;
	}

	private JPanel panelUsername() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(lblUsername, BorderLayout.WEST);
		panel.add(tfUsername, BorderLayout.CENTER);
		return panel;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLogIn) {
			//if-sats
			// om användarnamet finns = connecta & ta upp UIUsers
			// om användarnamet inte finns = ta bort text i tfUsername.
			ul = client.getList();
			int sameUsers = 0;
			
			for(int i = 0; i < ul.size(); i++) {
				if((ul.getUser(i).getName().equals( tfUsername.getText().trim()))) {
					sameUsers++;
				}
			}
			if(sameUsers == 0) {
				client.sendUser(tfUsername.getText().trim());
				frame.setVisible(false);
				System.out.println("UserCreated");
				
			}else {
				System.out.println("userExists");
				tfUsername.setText("");
				
			}
			
	
		}
	}

//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		JFrame frame = new JFrame("Log in");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
//		frame.setResizable(false);
//		frame.setPreferredSize(new Dimension(450,250));
//		frame.add(new UILogIn(new Client()));
//		frame.pack();
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//	}
}
