package chatServer;

import java.util.ArrayList;

import resources.UserMessage;

public class OfflineMessages {
	private ArrayList<UserMessage> storedMessages = new ArrayList<UserMessage>();
	
	public void add(UserMessage param) {
		storedMessages.add(param);
	}
	public ArrayList<UserMessage> receive(String name) {
		ArrayList<UserMessage> returnMessages = new ArrayList<UserMessage>();
		for(UserMessage message : storedMessages) {
			if(message.getReceivers().getUser(0).equals(name)) {
				returnMessages.add(message);
				storedMessages.remove(message);
			}
		}
		if(returnMessages.size() > 0)
			return returnMessages;
		return null;
	}
	public boolean checkName(String param) {
		for(UserMessage message : storedMessages) {
			if(message.getReceivers().getUser(0).equals(param))
				return true;
		}
		return false;
	}
}
