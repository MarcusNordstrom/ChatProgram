package resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

public class UserMessage implements Serializable{
	private static final long serialVersionUID = 3765135201332970311L;
	private String title;
	private String content;
	private ImageIcon image;
	private Date arrived;
	private Date delivered;
	private User user;
	private UserList receivers;
	
	public UserMessage(User user, UserList list, String content, ImageIcon image) {
		this.user = user;
		this.receivers = list;
		this.content = content;
		this.image = image;
	}
	
	public User getUser() {
		return this.user;
	}
	public UserList getReceivers() {
		return this.receivers;
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
	public String toString() {
        return "From User: " + this.user.getName() + "\n"
                + "To Users: " + this.receivers.toString() + "\n"
                + "Contains: \n" + this.content;
    }
}
