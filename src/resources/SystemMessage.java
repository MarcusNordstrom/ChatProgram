package resources;

import java.io.Serializable;
import java.util.ArrayList;

public class SystemMessage extends Message implements Serializable{
	private Object payload;
	private String instruction;
	
	
	/**
	 * Constructor for sending objects such as the new userlist to clients.
	 * @param user
	 * @param recivers
	 * @param payload
	 */
	public SystemMessage(User user, ArrayList<User> recivers, Object payload) {
		super(user, recivers);
		this.payload = payload;
		this.instruction = "UserListUpdate";
	}
	/**
	 * Constructor for sending system messages to the server/ client to tell them what to do.
	 * Possible commands:
	 * DISCONNECT //The client is disconnecting
	 * @param user
	 * @param recivers
	 * @param instruction
	 */
	public SystemMessage(User user, ArrayList<User> recivers, String instruction) {
		super(user, recivers);
		this.payload = null;
		this.instruction = instruction;
	}
	
	public SystemMessage(String instruction) {
		super();
		this.payload = null;
		this.instruction = instruction;
	}
	
	public Object getPayload() {
		return payload;
	}
	
	public String getInstruction() {
		return instruction;
	}
}
