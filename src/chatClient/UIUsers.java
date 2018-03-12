package chatClient;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import resources.UserList;

public class UIUsers extends JPanel implements ActionListener, ListSelectionListener {
	private JButton btnWrite = new JButton("Write message");
	private JButton btnContacts = new JButton("Add to contacts");
	private JButton btnDisconnect = new JButton("Log ut");

	private JLabel lblUsersOnline = new JLabel("Users online");
	private JLabel lblSavedUsers = new JLabel("Saved users");

	private JList list;

	private String[] online = new String[20];
	
	private JPanel panelOnline = new JPanel();

	private JRadioButton[] radioButtonsOnline;
	private JRadioButton[] radioButtonsSaved;

	private Client client;
	private UserList userList;
	
	public void updateOnline() {
		userList = client.getList();
		System.out.println(userList.size());
		online = new String[userList.size()];
		for(int i = 0; i < userList.size(); i++) {
			online[i] = userList.getUser(i).getName();
			System.out.println(userList.getUser(i).getName());
		}
			list.removeAll();
			list = new JList(online);
			list.setSelectedIndex(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			list.setVisibleRowCount(4);
			list.addListSelectionListener(this);
		
	}
	


	public UIUsers(Client client) {
		this.client = client;
		setLayout(new BorderLayout());
		add(panelTop(), BorderLayout.NORTH);
		add(panelCenter(), BorderLayout.CENTER);
		userList = client.getList();
		updateOnline();
	}

	private JPanel panelTop() {
		JPanel panel = new JPanel(new GridLayout(0,3));
		panel.add(btnWrite);
		panel.add(btnContacts);
		panel.add(btnDisconnect);
		return panel;
	}

	private JPanel panelOnline() {
		panelOnline = new JPanel(new BorderLayout());
		list = new JList(online);
		list.setSelectedIndex(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setVisibleRowCount(4);
		list.addListSelectionListener(this);

		panelOnline.add(lblUsersOnline, BorderLayout.NORTH);
		panelOnline.add(list, BorderLayout.CENTER);
		return panelOnline;
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



	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

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
