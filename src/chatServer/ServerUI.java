package chatServer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerUI extends JPanel implements ActionListener {

	private JTextArea jta = new JTextArea(12, 12);

	private JTextArea jtafrom = new JTextArea("Date from: yyyy/MM/dd HH:mm:ss");
	private JTextArea jtato = new JTextArea("Date to yyyy/MM/dd HH:mm:ss");

	// private JButton jbtExit = new JButton("EXIT");
	private JButton jbtLogg = new JButton("LOGG");
	private JScrollPane jsp = new JScrollPane(jta);
	private ArrayList<String> fullLogg = new ArrayList<String>();
	private ArrayList<String> logg = new ArrayList<String>();

	private TCPServer server;
	private OfflineWriter ow;
	JPanel jp = new JPanel();

	public ServerUI() {
		setLayout(new BorderLayout());
		add(jsp, BorderLayout.CENTER);
		// add(jbtExit, BorderLayout.WEST);
		add(jbtLogg, BorderLayout.EAST);
		add(jtato, BorderLayout.SOUTH);
		add(jtafrom, BorderLayout.NORTH);

		jbtLogg.addActionListener(this);
		// jbtExit.addActionListener(this);

	}

	public void fullLogg(ArrayList<String> logg) {
		fullLogg = logg;
		jta.setText("");
		for (String s : logg) {
			jta.setText(jta.getText() + s);
		}
	}

	public void logg(ArrayList<String> logg) {

		jta.setText("");
		for (String s : logg) {
			jta.setText(jta.getText() + s);
		}
	}

	public void uiToServer(TCPServer server) {
		this.server = server;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if(e.getSource() == jbtExit) {
		// ow.shutDown();
		// }

		if (e.getSource() == jbtLogg) {
			ArrayList<String> templogg = new ArrayList<String>();
			String allText = "";
			for(String s : this.logg) {
				allText += s;
			}
			String[] split = allText.split(";;");
			System.out.println(split.length);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd\\HH:mm:ss");
			Date dateLogg;
			Date dateStart;
			Date dateStop;
			try {
				if(!(jtafrom.getText().equals("")) || !(jtato.getText().equals(""))) {
				dateStart = sdf.parse(jtafrom.getText());
				dateStop = sdf.parse(jtato.getText());

				for (int i = 0; i < split.length; i += 2) {
					String temp = split[i];
					System.out.println(temp + " " + i);
					dateLogg = sdf.parse(temp);

					if (dateLogg.after(dateStart) && dateLogg.before(dateStop)) {
						templogg.add(split[i] + split[i + 1]);
					}

				}
				logg(templogg);
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
	}
}