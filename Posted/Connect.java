import java.util.Collection;
//import java.util.Scanner;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.*;


public class Connect {
	public static void main(String[] args) {

	}
	static XMPPConnection mainConnection = null;
	static Chat newChat = null;
	private Gui a = null;
	private Roster roster;
	
	public Connect(Gui g){
		a=g;
	}
	public boolean login(String username, String password) {
		ConnectionConfiguration config = new ConnectionConfiguration(
				"talk.google.com", 5222, "gmail.com");
		XMPPConnection connection = new XMPPConnection(config);
		try {
			connection.connect();
			connection.login(username, password);
			System.out.println("Login succesfful");
			mainConnection = connection;
			return true;
		}
		catch (XMPPException e1) {
			System.out.println("Error connecting");
			return false;
		}

	}
	public void startChat(String buddy) {

		ChatManager chatmanager = mainConnection.getChatManager();		
		newChat = chatmanager.createChat(buddy, new MessageListener() {
			public void processMessage(Chat chat, Message message) {
				String incomingMessage = message.getBody();
				if ( ! incomingMessage.equals("null")) {
				a.updateMessages(incomingMessage);
				}
			}
		});
	}
	
//	public void acceptTransmissions(){
//		mainConnection.addPacketListener(new MessageFromBuddyListener, new MessagePacketFilter);
//	}
	 
	public void sendMSG(String text){
		String msg = text;
		try {
			newChat.sendMessage(msg);
		} catch (XMPPException e) {
			System.out.println("Error Delivering message");
		}
	}
	
//	public static void acceptTransmissions() {
//		ChatManager chatManager = mainConnection.getChatManager().addChatListener(
//			    new ChatManagerListener() {
//			        @Override
//			        public void chatCreated(Chat chat, boolean createdLocally)
//			        {
//			            if (!createdLocally)
//			                chat.addMessageListener(new MyNewMessageListener());;
//			        }
//			    } );
//	}
	
	
	public  void getBuddies(User user) {  // Gets roster and adds it to the user's buddy array list
		User a = user;
		roster = mainConnection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
		for (RosterEntry entry : entries) {
			String buds = entry.getUser();
			//System.out.println(roster.getPresence(buds));
			a.addBuddy(buds);
		}
	}
	public void getBudStatus(String buddy){
		roster = mainConnection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		String status = roster.getPresence(buddy).toString();
		a.updateBuddySelect(status);
	}

	
	public void setStatus(String status) {
		Presence presence = new Presence(Presence.Type.available);
		presence.setStatus(status);
		mainConnection.sendPacket(presence);
	}
	
	// DOES NOT WORK 
	public  boolean addBuddy(User user, String budyUsername) {
		User b = user;
		roster = mainConnection.getRoster();
		String name = "name";   // do we need? 
		if (!roster.contains(budyUsername)) {
			try {
				roster.createEntry(budyUsername, name, null);
				b.addBuddy(budyUsername);
				return true;
			} 
			catch (XMPPException e) {
				e.printStackTrace();
				return false;
			}
		}
		else return false;
	} 
	
	// removes buddy from roster and the user's array list
	public void removeBuddy(User user, String buddyUsername) {
		User a = user;
		Roster roster = mainConnection.getRoster();
		if (roster.contains(buddyUsername)) {
			try {
				roster.removeEntry(roster.getEntry(buddyUsername)); // gets the roster entry from username then removes it
				a.removeBuddy(buddyUsername);
			} 
			catch (XMPPException e) {
				e.printStackTrace();
			}
		}
	}
	class MessageFromBuddyListener implements PacketListener {
		public void processPacket(Packet p) {
			String user = p.getFrom();
			user = user.substring(0, user.indexOf("/"));
			//chatList.add(user);
			RosterEntry r = mainConnection.getRoster().getEntry(user);
			
			//openChats.add(new Gui2(chatmanager, r, ((Message)p).getBody()));
			a.updateMessages(((Message)p).getBody());
		}
	}

	class MessagePacketFilter implements PacketFilter {
		public boolean accept(Packet p) {
			String user = p.getFrom();
			user = user.substring(0, user.indexOf("/"));
			if(p instanceof Message){ //&& !chatList.contains(user)) {
				return true;
			}
			return false;
		}
	}
}



