package resources;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;

import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

/**
 * This is the standard method of sending messages back and forth in the form of
 * UserMessage objects.
 * 
 * @author UncleBen
 *
 */
public class UserMessage implements Serializable {
	private static final long serialVersionUID = 3765135201332970311L;
	private String title;
	private String content;
	private ImageIcon image;
	private Date arrived;
	private Date delivered;
	private User user;
	private UserList receivers;

	/**
	 * Standard constructor for this Object.
	 * 
	 * @param user
	 *            The @User that this message is from.
	 * @param list
	 *            An @UserList containing a list of Users to send this message to.
	 * @param content
	 *            This is what the message contains.
	 * @param image
	 *            Used to send images through the chat
	 */
	public UserMessage(User user, UserList list, String content, ImageIcon image) {
		this.user = user;
		this.receivers = list;
		this.content = content;
		this.image = image;
	}

	/**
	 * 
	 * @return Returns the @User this message is from.
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * 
	 * @return Returns a @UserList of all the receivers.
	 */
	public UserList getReceivers() {
		return this.receivers;
	}

	/**
	 * @return Returns the @Date the message arrived.
	 */
	public Date getArrived() {
		return arrived;
	}

	/**
	 * Set the time the message arrived to the User.
	 * 
	 * @param arrived
	 */
	public void setArrived(Date arrived) {
		this.arrived = arrived;
	}

	/**
	 * @return Get the @Date the message initially delivered.
	 */
	public Date getDelivered() {
		return delivered;
	}

	/**
	 * Set the time the message delivered.
	 * 
	 * @param delivered
	 */
	public void setDelivered(Date delivered) {
		this.delivered = delivered;
	}

	/**
	 * @return Returns the title for this given message.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return Returns the content in this message
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return Returns the image for this message.
	 */
	public ImageIcon getImage() {
		return image;
	}

	/**
	 * Returns a String with From, To, Content and ImageIcon's toString
	 */
	public String toString() {
		return "From User: " + this.user.getName() + "\n" + "To Users: " + this.receivers.toString() + "\n"
				+ "Contains: \n" + this.content;
	}
}
