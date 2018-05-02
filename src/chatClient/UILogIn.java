package chatClient;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import resources.User;
import resources.UserList;

/**
 * The interface for creating a user and logging in. 
 * 
 */
public class UILogIn extends JPanel implements ActionListener {
	JFrame frame;
	private JLabel lblHeadline = new JLabel("Welcome to log in");
	private JLabel lblUsername = new JLabel("Username: ");
	private JTextField tfUsername = new JTextField();
	private JButton btnLogIn = new JButton("Log In");
	private JButton btnUserImage = new JButton("Choose user image");
	private Client client;
	private UserList ul = new UserList();
	private ImageIcon imageIcon = null;

	/**
	 * Constructor 
	 * @param client
	 * @param frame
	 */
	public UILogIn(Client client, JFrame frame) {
		this.client = client;
		this.frame = frame;
		setLayout(new BorderLayout());
		add(panelCenter(), BorderLayout.CENTER);
		btnLogIn.addActionListener(this);
		btnUserImage.addActionListener(this);
	}


	/**
	 * Adding a panel an 2 buttons to the window.
	 * @return panel 
	 * 			a panel with GridLayout and 3 components. 
	 */
	private JPanel panelCenter() {
		JPanel panel = new JPanel(new GridLayout(3,0));
		panel.add(panelTop());
		panel.add(btnUserImage);
		panel.add(btnLogIn);
		return panel;
	}


	/**
	 * Creating a panel with the headline and adding a panel 
	 * with a text field. 
	 * @return panel 
	 * 			a panel with GridLayout and 2 components. 
	 */
	private JPanel panelTop() {
		JPanel panel = new JPanel(new GridLayout(2,0));
		lblHeadline.setFont(new Font("Comic sans", Font.BOLD, 14));
		lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblHeadline);
		panel.add(panelUsername());
		return panel;
	}

	
	/**
	 * Creating a panel with a label and a text field
	 * where users can enter their username. 
	 * @return panel 	
	 *			a panel with BorderLayout and 2 components. 
	 */
	private JPanel panelUsername() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(lblUsername, BorderLayout.WEST);
		panel.add(tfUsername, BorderLayout.CENTER);
		return panel;
	}

	
	/**
	 * Creating functions for the buttons. 
	 * The method compares new usernames with already existing usernames.
	 * If they are the same, users need to enter another username to log in.
	 * It also makes it possible to choose an image from users computer. 
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLogIn && !(tfUsername.getText().trim().equals(""))) {
			ul = client.getList();
			int sameUsers = 0;

			for(int i = 0; i < ul.size(); i++) {
				if((ul.getUser(i).getName().equals( tfUsername.getText().trim()))) {
					sameUsers++;
				}
			}
			if(sameUsers == 0) {
				client.sendUser(new User(tfUsername.getText().trim(), imageIcon));
				frame.setVisible(false);

			}else {
				System.out.println("userExists");
				tfUsername.setText("");

			}


		}
		if(e.getSource()==btnUserImage) {
			try {
				JFileChooser filechooser = new JFileChooser();
				int result = filechooser.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					File file = filechooser.getSelectedFile();
					imageIcon = new ImageIcon(ImageIO.read(file));
					System.out.println(file.toString());
					imageIcon = new ImageIcon (imageIcon.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
					btnUserImage.setIcon(imageIcon);
					btnUserImage.setText("");
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Wrong file format \nPlease selecet .jpg, .png, .bnp, .gif");
			}
		}
	}
}

