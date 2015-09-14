import java.util.Collection;
//import java.util.Scanner;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;


public class Connect {
	public static void main(String[] args) {
//		String user = "cs2110test@gmail.com";
//		String pass = "softwaredevelopment";
//		User dude = new User(user);
//		Connect.login(user, pass);
//		getBuddies(dude);
//		addBuddy(dude,"merrill.hunter@gmail.com");
//		System.out.println(dude.getBuddyList());
//
//		startChat("merrill.hunter@gmail.com");
//		Scanner stdin = new Scanner(System.in);
//		System.out.print("Enter message: ");
//		String msg = stdin.nextLine();
//		while (! msg.startsWith("bye")) {
//			sendMSG(msg);
//			System.out.print("Enter message: ");
//			msg = stdin.nextLine();
//		}
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
//		roster = mainConnection.getRoster();
//		roster.addRosterListener(new RosterListener() {
//		    // Ignored events public void entriesAdded(Collection<String> addresses) {}
//		    public void entriesDeleted(Collection<String> addresses) {}
//		    public void entriesUpdated(Collection<String> addresses) {}
//		    public void presenceChanged(Presence presence) {
//		        System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
//		    }
//		});
		
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
	
	// does what its supposed to. 
	public void sendMSG(String text){
		String msg = text;
		try {
			newChat.sendMessage(msg);
		} catch (XMPPException e) {
			System.out.println("Error Delivering message");
		}
	}
	
//	public static void acceptTransmissions() {
//		ChatManager chatmanager = mainConnection.getChatManager().addChatListener(
//			    new ChatManagerListener() {
//			        @Override
//			        public void chatCreated(Chat chat, boolean createdLocally)
//			        {
//			            if (!createdLocally)
//			                chat.addMessageListener(new MyNewMessageListener());;
//			        }
//			    } );
//	}
	
	// Gets roster and adds it to the user's buddy array list
	public  void getBuddies(User user) { //throws InterruptedException {
		User a = user;
		roster = mainConnection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		for (RosterEntry entry : entries) {
			String buds = entry.getUser();
			//System.out.println(roster.getPresence(buds));
			a.addBuddy(buds);
		}
	}
	public void getBudStatus(String buddy){
		roster = mainConnection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
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
		Roster roster = mainConnection.getRoster();
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
}



