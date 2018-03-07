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

public class UIRegistration extends JPanel implements ActionListener {
	private JLabel lblHeadline = new JLabel("Choose username and image to finish your registration");
	private JLabel lblUsername = new JLabel("Username:");
	private JLabel lblUserImage = new JLabel("Choose image");
	private JLabel lblInfo = new JLabel("Username not avaliable");
	private JLabel lblEmpty = new JLabel(" ");
	private JTextField tfUsername = new JTextField();
	private JButton btnUserImage = new JButton(new ImageIcon("images/userImage.png"));
	private JButton btnOK = new JButton("OK");


	public UIRegistration() {
		setLayout(new BorderLayout());
		lblHeadline.setFont(new Font("Comic sans", Font.BOLD, 14));
		lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
		add(panelTop(), BorderLayout.NORTH);
		add(panelCenter(), BorderLayout.CENTER);
		add(btnOK, BorderLayout.SOUTH);
		btnOK.addActionListener(this);
		btnUserImage.addActionListener(this);

	}


	private JPanel panelTop() {
		JPanel panel = new JPanel(new GridLayout(3,0));
		lblHeadline.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		//lblInfo.hide();
		lblInfo.setForeground(Color.RED);
		panel.add(lblEmpty);
		panel.add(lblHeadline);
		panel.add(lblInfo);
		return panel;
	}

	private JPanel panelCenter() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(panelUsername(), BorderLayout.NORTH);
		panel.add(panelUserImage(), BorderLayout.CENTER);
		return panel;
	}

	private JPanel panelUsername() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(lblUsername, BorderLayout.WEST);
		panel.add(tfUsername, BorderLayout.CENTER);
		return panel;
	}

	private JPanel panelUserImage() {
		JPanel panel = new JPanel(new GridLayout(2,0));
		lblUserImage.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblUserImage);
		panel.add(btnUserImage);
		return panel;
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOK) {
			// 	if-sats
			// om användarnamnet är ledigt = stängs fönster och fönster med uppkopplade användare syns
			// om användarnamnet är upptaget skrivs det ut i lblInfo och tfUsername töms. 
		}
		if(e.getSource() == btnUserImage) {
			// får upp katalog och kan välja ett foto
		}

	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Registration");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setResizable(false); 
		frame.setPreferredSize(new Dimension(450,250));
		frame.add(new UIRegistration());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}



}
