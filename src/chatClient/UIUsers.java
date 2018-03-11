package chatClient;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class UIUsers extends JPanel implements ActionListener {
	private JButton btnWrite = new JButton("Write message");
	private JButton btnContacts = new JButton("Add to contacts");
	private JButton btnDisconnect = new JButton("Log ut");

	private JLabel lblUsersOnline = new JLabel("Users online");
	private JLabel lblSavedUsers = new JLabel("Saved users");

	private JRadioButton[] radioButtonsOnline;
	private JRadioButton[] radioButtonsSaved;

	private Client client;


	public UIUsers(Client client) {
		this.client = client;
		setLayout(new BorderLayout());
		add(panelTop(), BorderLayout.NORTH);
		add(panelCenter(), BorderLayout.CENTER);
	}


	private JPanel panelTop() {
		JPanel panel = new JPanel(new GridLayout(0,3));
		panel.add(btnWrite);
		panel.add(btnContacts);
		panel.add(btnDisconnect);
		return panel;
	}

	private JPanel panelOnline() {
		JPanel panel = new JPanel(new GridLayout(6,0));	// kan man sätta antalet klienter här + 1 för vår Label? 
		radioButtonsOnline = new JRadioButton[5];				// istället för 5 antalet inloggade klienter
		panel.add(lblUsersOnline);
		for (int i = 0; i < radioButtonsOnline.length; i++) {
			radioButtonsOnline[i] = new JRadioButton("" + i);	// istället för i klientens namn
			panel.add(radioButtonsOnline[i]);
		}
		return panel;
	}

	private JPanel panelSavedUsers() {
		JPanel panel = new JPanel(new GridLayout(6,0));	// kan man sätta antalet klienter här + 1 för vår Label? 
		radioButtonsSaved = new JRadioButton[5];				// istället för 5 antalet sparade klienter
		panel.add(lblSavedUsers);
		for (int i = 0; i < radioButtonsSaved.length; i++) {
			radioButtonsSaved[i] = new JRadioButton("" + i);	// istället för i klientens namn
			panel.add(radioButtonsSaved[i]);
		}
		return panel;
	}

	private JPanel panelCenter() {
		JPanel panel = new JPanel(new GridLayout(2,0));
		panel.add(panelOnline());
		panel.add(panelSavedUsers());		
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
