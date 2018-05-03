package resources;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * User Object containing name and an ImageIcon.
 * 
 * @author UncleBen
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 7626251507713469846L;
	private String name;
	private ImageIcon pic = null;

	/**
	 * Standard constructor for creating a User
	 * 
	 * @param name
	 *            Insert the name for this User
	 * @param pic
	 *            Insert an ImageIcon for this User, can be null.
	 */
	public User(String name, ImageIcon pic) {
		this.name = name;
		this.pic = pic;
	}

	/**
	 * Used to change the current picture for the User. Never used.
	 * 
	 * @param pic
	 */
	@Deprecated
	public void setPicure(ImageIcon pic) {
		this.pic = pic;
	}

	/**
	 * Used to change the name of the User Never used.
	 * 
	 * @param name
	 */
	@Deprecated
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of this User object.
	 * 
	 * @return Name.
	 */
	public String getName() {
		return new String(this.name);
	}

	/**
	 * Returns the ImageIcon for this User
	 * 
	 * @return ImageIcon
	 */
	public ImageIcon getPic() {
		return new ImageIcon(pic.getImage());
	}

	/**
	 * @return The Hashcode for the Name.
	 */
	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * Basic equals method to compare 2 users hashcodes.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			if (name.hashCode() == user.hashCode())
				return true;
		}
		return false;
	}

	/**
	 * Clones the current User and returns a new.
	 * 
	 * @return A new User Object
	 */
	public User clone() {
		return new User(this.name, this.pic);
	}
}