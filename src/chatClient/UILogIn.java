package chatClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import resources.User;
import resources.UserList;

public class UILogIn extends JPanel implements ActionListener {
	private JLabel lblHeadline = new JLabel("Welcome to log in");
	private JLabel lblUsername = new JLabel("Username: ");
	private JTextField tfUsername = new JTextField();
	private JButton btnLogIn = new JButton("Log In");
	private JButton btnUserImage = new JButton("Choose user image");
	private Client client;
	private UserList ul = new UserList();
	JFrame frame;
	private ImageIcon imageIcon = null;


	public UILogIn(Client client, JFrame frame) {
		this.client = client;
		this.frame = frame;
		setLayout(new BorderLayout());
		add(panelCenter(), BorderLayout.CENTER);
		btnLogIn.addActionListener(this);
		btnUserImage.addActionListener(this);
	}

	private JPanel panelCenter() {
		JPanel panel = new JPanel(new GridLayout(3,0));
		panel.add(panelTop());
		panel.add(btnUserImage);
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
		if(e.getSource() == btnLogIn && !(tfUsername.getText().trim().equals(""))) {
			//if-sats
			// om anv�ndarnamet finns = connecta & ta upp UIUsers
			// om anv�ndarnamet inte finns = ta bort text i tfUsername.
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

