package chatClient;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import resources.User;
import resources.UserList;
import resources.UserMessage;


public class UIChat extends JPanel implements ActionListener, Observer {
	private JScrollPane scroll = new JScrollPane();
	private JLabel lblReceiver = new JLabel("");
	private JTextArea taMessage = new JTextArea("");
	private JTextArea taWrite = new JTextArea();
	private JButton btnSend = new JButton("Send");
	private JButton btnAppend = new JButton(new ImageIcon("images/gem.png"));

	private ImageIcon sendingImage;
	private UserList receivers;

	private Client client;

	public UIChat(Client client, String receiver, UserList retList) {
		this.client = client;
		client.addObserver(this);
		lblReceiver.setText(receiver);
		receivers = retList;
//		receivers.addUser(new User(receiver, new ImageIcon()));
		setLayout(new BorderLayout());
		add(panelTop(), BorderLayout.NORTH);
		add(panelCenter(), BorderLayout.CENTER);
		add(panelBottom(), BorderLayout.SOUTH);
		btnSend.addActionListener(this);
		btnAppend.addActionListener(this);
	}


	private JPanel panelTop() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1,30));
		panel.add(lblReceiver, BorderLayout.CENTER);
		return panel;
	}

	private JPanel panelCenter() {
		JPanel panel = new JPanel(new BorderLayout());
		scroll = new JScrollPane(taMessage);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		taMessage.setLineWrap(true);
		taMessage.setWrapStyleWord(true);
		taMessage.setEditable(false);
		panel.setBackground(Color.WHITE);
		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}

	private JPanel panelBottom() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1,80));
		scroll = new JScrollPane(taWrite);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		taWrite.setLineWrap(true);
		taWrite.setWrapStyleWord(true);
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(btnSend, BorderLayout.EAST);
		panel.add(btnAppend, BorderLayout.WEST);
		return panel;
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btnSend) {
			String message = taWrite.getText().trim();

			client.send(new UserMessage(client.getSelf(), receivers, message, sendingImage));
			taWrite.setText("");
		}
		if(e.getSource() == btnAppend) {
			JFileChooser filechooser = new JFileChooser();
			int result = filechooser.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				try {
					sendingImage = new ImageIcon(ImageIO.read(file));
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof UserMessage) {
			UserMessage um = (UserMessage)arg1;
			System.out.println(um.toString());
			if(um.getUser().equals(receivers.getUser(0))){
				taMessage.append(um.getContent()+"\n");
			}
		}
		
	}
}
