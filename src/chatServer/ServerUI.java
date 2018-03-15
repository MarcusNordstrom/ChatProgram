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
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import chatClient.UIChat;

public class ServerUI extends JPanel implements ActionListener {

	private JTextArea jta = new JTextArea();
	
	private JTextArea jtafrom = new JTextArea("Enter logg-date from:");
	private JTextArea jtato = new JTextArea("Enter logg-date to");
	
	private JButton jbtExit = new JButton("EXIT");
	private JButton jbtLogg = new JButton("LOGG");

	private OfflineWriter ow;
	JPanel jp = new JPanel();

	public ServerUI() {
		setLayout(new BorderLayout());
		add(jta, BorderLayout.CENTER);
		add(jbtExit, BorderLayout.WEST);
		add(jbtLogg, BorderLayout.EAST);
		add(jtafrom, BorderLayout.SOUTH);
		add(jtato,BorderLayout.NORTH);
		
		jbtLogg.addActionListener(this);
		jbtExit.addActionListener(this);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jbtExit) {
			ow.shutDown();
		}

		if(e.getSource() == jbtLogg) {
			String str;
			try {
				BufferedReader br = new BufferedReader(new FileReader("Logging"));
				 StringBuilder sb = new StringBuilder();
				    String line = br.readLine();

				    while (line != null) {
				        sb.append(line);
				        sb.append(System.lineSeparator());
				        line = br.readLine();
				    }
				    str = sb.toString();
				    jta.setText(str);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}