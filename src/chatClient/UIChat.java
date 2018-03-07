package chatClient;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;


public class UIChat extends JPanel implements ActionListener {
	private JScrollPane scroll = new JScrollPane();
	private Choice choice = new Choice();
	private JTextArea taMessage = new JTextArea("");
	private JTextArea taWrite = new JTextArea();
	private JButton btnSend = new JButton("Send");
	private JButton btnAppend = new JButton(new ImageIcon("images/gem.png"));
	
	
	private Controller controller = new Controller();

	public UIChat() {
		setLayout(new BorderLayout());
		add(panelTop(), BorderLayout.NORTH);
		add(panelCenter(), BorderLayout.CENTER);
		add(panelBottom(), BorderLayout.SOUTH);
		btnSend.addActionListener(this);
	}

	private void choices() {
		for(int i= 0; i <= 5; i++) {
			choice.add(new String(""+i));
		}
	}

	private JPanel panelTop() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(1,30));
		choice.add(new String("Choose receiver"));
		choices();
		panel.add(choice, BorderLayout.CENTER);
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

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSend) {
			String message = taWrite.getText().trim();
			controller.message(message);
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Chat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setPreferredSize(new Dimension(600,400));
		frame.add(new UIChat());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	


}
