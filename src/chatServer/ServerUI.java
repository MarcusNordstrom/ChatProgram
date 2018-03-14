package chatServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import chatClient.UIChat;

public class ServerUI extends JPanel implements ActionListener {

	private JTextArea jta = new JTextArea(12, 12);

	private JTextArea jtafrom = new JTextArea("Enter logg-date from:");
	private JTextArea jtato = new JTextArea("Enter logg-date to");

	private JButton jbtExit = new JButton("EXIT");
	private JButton jbtLogg = new JButton("LOGG");
	private JScrollPane jsp = new JScrollPane(jta);
	private ArrayList<String> logg = new ArrayList<String>();

	private TCPServer server;
	private OfflineWriter ow;
	JPanel jp = new JPanel();

	public ServerUI() {
		setLayout(new BorderLayout());
		add(jsp, BorderLayout.CENTER);
		add(jbtExit, BorderLayout.WEST);
		add(jbtLogg, BorderLayout.EAST);
		add(jtato, BorderLayout.SOUTH);
		add(jtafrom,BorderLayout.NORTH);


		jbtLogg.addActionListener(this);
		jbtExit.addActionListener(this);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jbtExit) {
			ow.shutDown();
		}

		if(e.getSource() == jbtLogg) {
			String str = null;
			String test = null;



			try {
				BufferedReader br = new BufferedReader(new FileReader("Logging"));
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
					logg.add(line);
				}
				str = sb.toString();
				jta.setText(str);


			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}