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
import javax.swing.ScrollPaneConstants;

import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class ServerUI extends JPanel implements ActionListener {

	private JTextArea jta = new JTextArea(12, 12);

	private JTextArea jtafrom = new JTextArea("yyyy/MM/dd HH:mm:ss");
	private JTextArea jtato = new JTextArea("yyyy/MM/dd HH:mm:ss");
	private JButton jbtLogg = new JButton("LOGG");
	private JScrollPane jsp = new JScrollPane(jta);
	private ArrayList<String> fullLogg = new ArrayList<String>();
	private ArrayList<String> logg = new ArrayList<String>();
	private TCPServer server;
	private OfflineWriter ow;
	private JPanel jp = new JPanel();

	public ServerUI() {
		setLayout(new BorderLayout());
		add(jsp, BorderLayout.CENTER);
		add(jbtLogg, BorderLayout.EAST);
		add(jtato, BorderLayout.SOUTH);
		add(jtafrom, BorderLayout.NORTH);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		jsp.setAutoscrolls(true);
		jbtLogg.addActionListener(this);
	}

	/**
	 * Displays server logg in UI.
	 * @param logg
	 */
	public void fullLogg(ArrayList<String> logg) {
		fullLogg = logg;
		jta.setText("");
		for (String s : fullLogg) {
			jta.setText(jta.getText() + s);
		}
		reLogg();
	}

	/**
	 * Displays chosen logg in UI
	 * @param logg
	 */
	public void logg(ArrayList<String> logg) {
		jta.setText("");
		for (String s : logg) {
			jta.setText(jta.getText() + s);
		}
	}

	/**
	 * UI get server as return.
	 * @param server
	 */
	public void uiToServer(TCPServer server) {
		this.server = server;
	}

	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jbtLogg) {
			reLogg();
		}
	}
	
	/**
	 * filters the dates if they are given. Otherwise it loggs the full logg.
	 */
	private void reLogg() {
		ArrayList<String> templogg = new ArrayList<String>();
		String allText = "";
		for (String s : fullLogg) {
			allText += s;
		}
		String[] split = allText.split(";;");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date dateLogg;
		Date dateStart;
		Date dateStop;
		try {
			if ( !(jtafrom.getText().equals("")) && !(jtato.getText().equals("")) && !(jtafrom.getText().equals("yyyy/MM/dd HH:mm:ss")) && 
					!(jtato.getText().equals("yyyy/MM/dd HH:mm:ss") ) ) {
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
			}else {
				String all = "";
				for(String str : fullLogg) {
					all+=str;
				}
				String[] temp = all.split(";;");
				ArrayList<String> repaint = new ArrayList<String>();
				for(String s : temp) {
					repaint.add(s);
				}
				logg(repaint);
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

	}
}