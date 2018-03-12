package chatClient;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UIChat extends JPanel implements ActionListener {
	private JScrollPane scroll = new JScrollPane();
	private JLabel lblReceiver = new JLabel("");
	private JTextArea taMessage = new JTextArea("");
	private JTextArea taWrite = new JTextArea();
	private JButton btnSend = new JButton("Send");
	private JButton btnAppend = new JButton(new ImageIcon("images/gem.png"));

	private ImageIcon sendingImage;

	private Client client;

	public UIChat(Client client) {
		this.client = client;
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

			client.send(message);
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

		//	public static void main(String[] args) {
		//		try {
		//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//		try {
		//			Client client = new Client("192.168.1.55",90);
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		//		JFrame frame = new JFrame("Chat");
		//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//		frame.setPreferredSize(new Dimension(600,400));
		//		frame.add(new UIChat(client));
		//		frame.pack();
		//		frame.setLocationRelativeTo(null);
		//		frame.setVisible(true);
		//	}


	}
}
