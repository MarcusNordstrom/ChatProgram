package chatClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
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
	private String[] savedUsers = new String[20];

	private JPanel panelOnline = new JPanel();
	private JPanel panelSavedUsers = new JPanel();
	
	private JFrame frame;
	
	

	private Client client;
	private UserList userListOnline;
	private UserList userListSaved;

	
	public UIUsers(Client client, JFrame frame) {
		this.client = client;
		this.frame = frame;
		setLayout(new BorderLayout());
		add(panelTop(), BorderLayout.NORTH);
		add(panelCenter(), BorderLayout.CENTER);
		
		btnWrite.addActionListener(this);
		btnContacts.addActionListener(this);
		btnDisconnect.addActionListener(this);
		
		list.addListSelectionListener(this);
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
		userListOnline = client.getList();
		for(int i=0; i<userListOnline.size(); i++) {
			online[i] = userListOnline.getUser(i).getName();
		}

		list = new JList(online);
		list.setSelectedIndex(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		panelOnline.add(lblUsersOnline, BorderLayout.NORTH);
		panelOnline.add(list, BorderLayout.CENTER);
		return panelOnline;
	}


	private JPanel panelSavedUsers() {
		panelSavedUsers = new JPanel(new BorderLayout());	 
//		userListSaved = 
//		for(int i=0; i<userListSaved.size(); i++) {
//			savedUsers[i] = userListSaved.getUser(i).getName();
//		}
		
		list = new JList(savedUsers);
		list.setSelectedIndex(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		panelSavedUsers.add(lblSavedUsers, BorderLayout.NORTH);
		panelSavedUsers.add(list, BorderLayout.CENTER);
		return panelSavedUsers;
	}

	private JPanel panelCenter() {
		JPanel panel = new JPanel(new GridLayout(2,0));
		panel.add(panelOnline());
		panel.add(panelSavedUsers());		
		return panel;
	}



	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnWrite) {
			String receiver ;

			frame = new JFrame();
			frame.setPreferredSize(new Dimension(600,400));
			frame.add(new UIChat(client));
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		
	
		} if(e.getSource() == btnContacts) {
			
		} if(e.getSource() == btnDisconnect) {
			client.exit();
		}

	}



	public void valueChanged(ListSelectionEvent e) {}
}
