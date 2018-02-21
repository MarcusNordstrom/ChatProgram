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

	public int hashCode() {
		return name.hashCode();
	}

	public boolean equals(Object obj) {
		return name.equals(obj);
	}
}