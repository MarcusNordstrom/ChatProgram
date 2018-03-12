package chatClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import resources.User;
import resources.UserList;

public class UIUsers extends JPanel implements ActionListener, ListSelectionListener,Observer {
	private JButton btnWrite = new JButton("Write message");
	private JButton btnContacts = new JButton("Add to contacts");
	private JButton btnDisconnect = new JButton("Log ut");

	private JLabel lblUsersOnline = new JLabel("Users online");
	private JLabel lblSavedUsers = new JLabel("Saved users");
	
	private JTextPane tp = new JTextPane();
	
	private JList<String> list;

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
		
        client.addObserver(this);
		
		list.addListSelectionListener(this);
	}

	private JPanel panelTop() {
		JPanel panel = new JPanel(new GridLayout(2,3));
		panel.add(btnWrite);
		panel.add(btnContacts);
		panel.add(btnDisconnect);
		panel.add(tp);
		return panel;
	}

	private JPanel panelOnline() {
		panelOnline = new JPanel(new BorderLayout());
		userListOnline = client.getList();
		for(int i=0; i<userListOnline.size(); i++) {
			online[i] = userListOnline.getUser(i).getName();
		}

		list = new JList<String>(online);
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
		
		list = new JList<String>(savedUsers);
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
//			String receiver;
//			List receivers = list.getSelectedValuesList();
//			for(int i = 0; i < receivers.size(); i++) {
//				receiver = (String)receivers.get(i);
//				System.out.println(receiver);
//				System.out.println(receivers.get(i));
//			String receiver = "Test";
//			int[] selectedIndex = list.getSelectedIndices();
//			System.out.println(selectedIndex.length+"" + selectedIndex);
//			for( int i = 0; i < selectedIndex.length; i++) {	
//			System.out.println(client.getList().getUser(list.getSelectedIndices()[i]).getName());
			
			
			String receivers = tp.getText();	
			String[] receivArr = receivers.split(",");
			UserList retList = new UserList();
			for(String s : receivArr) {
				retList.addUser(new User(s, new ImageIcon()));
			}
				frame = new JFrame();
				frame.setPreferredSize(new Dimension(600,400));
				frame.add(new UIChat(client, receivers, retList));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
//			}
			
		
	
		} if(e.getSource() == btnContacts) {
			
		} if(e.getSource() == btnDisconnect) {
			client.exit();
		}

	}


	public void valueChanged(ListSelectionEvent e) {}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof UserList) {
			System.out.println("Receive userlist from client");
			UserList updater = (UserList)arg;
			online = new String[(updater.size()+1)];
			online[0] = " ";
			for(int i=0; i<updater.size(); i++) {
				online[(i+1)] = updater.getUser(i).getName();
				System.out.print(online[(i+1)] + " ");
			}
			System.out.println();
			
			list = new JList<String>(online);
			list.setSelectionMode(JList.VERTICAL);
			list.setSelectedIndex(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			
		}
		
	}
}
