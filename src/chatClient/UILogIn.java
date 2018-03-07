package chatClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class UILogIn extends JPanel implements ActionListener {
	private JLabel lblHeadline = new JLabel("Welcome to log in");
	private JLabel lblUsername = new JLabel("Username: ");
	private JTextField tfUsername = new JTextField();
	private JButton btnLogIn = new JButton("Log In");


	public UILogIn() {
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
	
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Log in");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(450,250));
		frame.add(new UILogIn());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
