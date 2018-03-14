package chatClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import resources.UserMessage;

/**
 * This is the interface for users to see other online users and saved contacts.
 * From here it´s possible to open a new message (UIChat), save online users and
 * quit the program.
 * 
 */
public class UIUsers extends JPanel implements ActionListener, ListSelectionListener, Observer {
	private JButton btnWrite = new JButton("Write message");
	private JButton btnContacts = new JButton("Add to contacts");
	private JButton btnDisconnect = new JButton("Exit");
	private JLabel lblUsersOnline = new JLabel("Users online");
	private JLabel lblSavedUsers = new JLabel("Saved users");
	private JTextPane writetp = new JTextPane();
	private JTextPane onlinetp = new JTextPane();
	private JTextPane offlinetp = new JTextPane();
	private String[] online = new String[20];
	private String[] savedUsers = new String[20];
	private JPanel panelOnline = new JPanel();
	private JPanel panelSavedUsers = new JPanel();
	private JFrame frame;

	private Client client;
	private UserList userListOnline;
	private UserList userListSaved;
	private ArrayList<UIChat> chattList = new ArrayList<UIChat>();

	
	/**
	 * Constructor 
	 * @param client
	 * @param frame
	 */
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
	}

	/**
	 * Creating a panel with 3 buttons and a text field.
	 * @return panel
	 * 			a panel with GridLayout and 4 components.
	 */		
	private JPanel panelTop() {
		JPanel panel = new JPanel(new GridLayout(2, 3));
		panel.add(btnWrite);
		panel.add(btnContacts);
		panel.add(btnDisconnect);
		panel.add(writetp);
		return panel;
	}
	
	/**
	 * Creating a panel that shows other online users.
	 * @return panel
	 * 			a panel with BorderLayout and 2 components.
	 */
	private JPanel panelOnline() {
		panelOnline = new JPanel(new BorderLayout());
		userListOnline = client.getList();
		onlinetp.setText("");
		onlinetp.setEditable(false);
		for (int i = 0; i < userListOnline.size(); i++) {
			onlinetp.setText(onlinetp.getText() + userListOnline.getUser(i).getName() + "\n");
		}
		panelOnline.add(lblUsersOnline, BorderLayout.NORTH);
		panelOnline.add(onlinetp, BorderLayout.CENTER);
		return panelOnline;
	}
	
	/**
	 * Creating a panel that shows saved users. 
	 * @return panel	
	 * 			a panel with BorderLayout and 2 components.
	 */
	private JPanel panelSavedUsers() {
		panelSavedUsers = new JPanel(new BorderLayout());
		panelSavedUsers.add(lblSavedUsers, BorderLayout.NORTH);
		panelSavedUsers.add(offlinetp, BorderLayout.CENTER);
		// panelSavedUsers.add(list, BorderLayout.CENTER);
		return panelSavedUsers;
	}

	/**
	 * Adding 2 panels that should be shown in the window.
	 * @return panel
	 * 			a panel with GridLayout and 2 components.
	 */
	private JPanel panelCenter() {
		JPanel panel = new JPanel(new GridLayout(2, 0));
		panel.add(panelOnline());
		panel.add(panelSavedUsers());
		return panel;
	}
	
	/**
	 * Creating functions for the buttons.
	 * Opens the window UIChat when button "write" is pressed. 
	 * Adding the receiver based on what was written in the text field.
	 * Disconnect if button "exit" is pressed.
	 * Adding user to contacts if button "add to contacts" is pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnWrite) {
			String receivers = writetp.getText();
			String[] receivArr = receivers.split(",");
			UserList retList = new UserList();
			// user inputed receivers
			for (String s : receivArr) {
				// compare to online users
				for (String res : receivArr) {
					for (int i = 0; i < userListOnline.size(); i++) {
						if (s.equals(userListOnline.getUser(i).getName())) {
							retList.addUser(userListOnline.getUser(i));
						}
					}
				}
			}
			UIChat chat = new UIChat(client, receivers, retList, this);
			frame = new JFrame();
			frame.setPreferredSize(new Dimension(600, 400));
			frame.add(chat);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			chattList.add(chat);
			// }

		}
		if (e.getSource() == btnContacts) {

		}
		if (e.getSource() == btnDisconnect) {
			client.exit();
		}

	}

	public void valueChanged(ListSelectionEvent e) {}
	
	/**
	 * Observes if new users are online and updates the list 
	 * with online users.
	 */
	public void update(Observable o, Object arg) {
		if (arg instanceof UserList) {
			System.out.println("Receive userlist from client");
			userListOnline = (UserList) arg;
			userListOnline = client.getList();
			onlinetp.setText("");
			for (int i = 0; i < userListOnline.size(); i++) {
				onlinetp.setText(onlinetp.getText() + userListOnline.getUser(i).getName() + "\n");
			}
		} else if (arg instanceof UserMessage) {
			UserMessage um = (UserMessage) arg;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int i = 0;
			for (UIChat frame : chattList) {
				if (frame.isReceiver()) {
					i++;
				}
			}
			if (i == 0) {
				JFrame chatFrame = new JFrame();
				UserList ul = new UserList();
				ul.addUser(um.getUser());
				UIChat chat = new UIChat(client, um.getUser().getName(), ul, this);
				chatFrame = new JFrame();
				chatFrame.setPreferredSize(new Dimension(600, 400));
				chatFrame.add(chat);
				chatFrame.pack();
				chatFrame.setLocationRelativeTo(null);
				chatFrame.setVisible(true);
				chat.appendTextArea(um);
				chattList.add(chat);
			}
		}

	}

	public void closeChat(String res) {
		for(int i = 0; i < chattList.size(); i++) {
			if(chattList.get(i).getResName().equals(res)) {
				System.out.println("removing chat with: " + chattList.get(i).getResName() + " equal to " + res);
				chattList.remove(i);

			}
		}

	}
}
