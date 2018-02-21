package resources;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

public class UserMessage extends Message{
	private String title;
	private String content;
	private ImageIcon image;
	private Date arrived;
	private Date delivered;
	
	public UserMessage(User user, ArrayList<User> recivers, String title, String content, ImageIcon image) {
		super(user, recivers);
		this.title = title;
		this.content = content;
		this.image = image;
	}

	public Date getArrived() {
		return arrived;
	}

	public void setArrived(Date arrived) {
		this.arrived = arrived;
	}

	public Date getDelivered() {
		return delivered;
	}

	public void setDelivered(Date delivered) {
		this.delivered = delivered;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public ImageIcon getImage() {
		return image;
	}
	
	
}
