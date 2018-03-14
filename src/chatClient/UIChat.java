package chatClient;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import resources.User;
import resources.UserList;
import resources.UserMessage;

public class UIChat extends JPanel implements ActionListener, KeyListener, Observer {
	private JScrollPane scroll = new JScrollPane();
	private JLabel lblReceiver = new JLabel("");
	private JTextPane taMessage = new JTextPane();
	private JTextArea taWrite = new JTextArea();
	private JButton btnSend = new JButton("Send");
	private JButton btnAppend = new JButton(new ImageIcon("images/gem.png"));
	private JButton btnClose = new JButton("Close");

	private ImageIcon sendingImage;
	private UserList receivers;

	private UIUsers ui;

	private Client client;
	private boolean isReceiver = false;
	private int offset;
	private JFrame frame;

	public UIChat(Client client, String receiver, UserList retList, UIUsers ui, JFrame frame) {
		this.client = client;
		this.frame = frame;
		this.ui = ui;
		client.addObserver(this);
		lblReceiver.setText(receiver);
		receivers = retList;
		// receivers.addUser(new User(receiver, new ImageIcon()));
		setLayout(new BorderLayout());
		add(panelTop(), BorderLayout.NORTH);
		add(panelCenter(), BorderLayout.CENTER);
		add(panelBottom(), BorderLayout.SOUTH);

		btnSend.addActionListener(this);
		btnAppend.addActionListener(this);
		btnClose.addActionListener(this);
		taWrite.addKeyListener(this);

		sendingImage = null;

		boolean scrollDown = textAreaBottomIsVisible();
		if (scrollDown) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					scrollToBottom();
				}
			});
		}
	}

	private JPanel panelTop() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1, 30));
		panel.add(lblReceiver, BorderLayout.CENTER);
		panel.add(btnClose, BorderLayout.EAST);
		return panel;
	}

	private JPanel panelCenter() {
		JPanel panel = new JPanel(new BorderLayout());
		scroll = new JScrollPane(taMessage);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		taMessage.setEditable(false);
		panel.setBackground(Color.WHITE);
		panel.add(scroll, BorderLayout.CENTER);
		return panel;
	}

	private JPanel panelBottom() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1, 80));
		scroll = new JScrollPane(taWrite);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		taWrite.setLineWrap(true);
		taWrite.setWrapStyleWord(true);
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(btnSend, BorderLayout.EAST);
		panel.add(btnAppend, BorderLayout.WEST);
		return panel;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSend) {
			System.out.println("Sending message...1");
			String message = taWrite.getText().trim();
			System.out.println("2 " + message);
			client.send(new UserMessage(client.getSelf(), receivers, message, sendingImage));

			taWrite.setText("");

			try {
				String resMess = ("you" + ":  " + message + "\n");
				taMessage.getStyledDocument().insertString(offset, resMess, null);
				offset += resMess.length();
				if (sendingImage != null) {
					taMessage.insertIcon(sendingImage);
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			// taMessage.append("You: " + message + "\n");
			sendingImage = null;
		}
		if (e.getSource() == btnAppend) {
			JFileChooser filechooser = new JFileChooser();
			int result = filechooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();
				try {
					sendingImage = new ImageIcon(ImageIO.read(file));
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}
		if (e.getSource() == btnClose) {
			ui.closeChat(lblReceiver.getText());
			frame.dispose();
		}
	}


	public void scrollToBottom() {
		JScrollBar bar = scroll.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
	}

	private boolean textAreaBottomIsVisible() {
		Adjustable sb = scroll.getVerticalScrollBar();
		int val = sb.getValue();
		int lowest = val + sb.getVisibleAmount();
		int maxVal = sb.getMaximum();
		boolean atBottom = maxVal == lowest;
		return atBottom;
	}

	public void appendTextArea(UserMessage message) {
		try {
			String resMess = (message.getUser().getName() + ":  " + message.getContent() + "\n");
			taMessage.getStyledDocument().insertString(offset, resMess, null);
			offset += resMess.length();
			if (sendingImage != null) {
				taMessage.insertIcon(sendingImage);
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		isReceiver = false;
		if (arg1 instanceof UserMessage) {
			UserMessage um = (UserMessage) arg1;
			System.out.println(um.toString());
			for (int i = 0; i < receivers.size(); i++) {
				if (um.getUser().getName().equals(lblReceiver.getText())) {
					appendTextArea(um);
					isReceiver = true;
				}
			}
		}
	}

	public boolean isReceiver() {
		return isReceiver;
	}

	public String getResName() {
		return lblReceiver.getText();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("Sending message...1");
			String message = taWrite.getText().trim();
			System.out.println("2 " + message);
			client.send(new UserMessage(client.getSelf(), receivers, message, sendingImage));
			taWrite.setText("");
		}
	}

	public void keyReleased(KeyEvent e) {
	}

}
