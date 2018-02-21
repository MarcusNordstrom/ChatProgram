package resources;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class User implements Serializable {
	private String name;
	private ImageIcon pic;

	public User(String name, ImageIcon pic) {
		this.name = name;
		this.pic = pic;
	}

	public void setPicure(ImageIcon pic) {
		this.pic = pic;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return new String(this.name);
	}

	public ImageIcon getPic() {
		return new ImageIcon(pic.getImage());
	}

	public int hashCode() {
		return name.hashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof User) {
			User user = (User)obj;
			if(this.hashCode() == user.hashCode())
				return true;
		}
		return false;
	}
}