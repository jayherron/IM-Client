import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class ChatApp {
	private String email;
	private String password;
	private Login login;
	private XMPPConnection connection;
	public ChatWindow chatWindow;
	public Roster roster;
	public ArrayList<RosterEntry> list;
	public Chat chat;
	public String buddy;
	public String messageText;

	public ChatApp() {

	}

	public static void main(String[] args) {

		ChatApp app = new ChatApp();
		Login frame = new Login(app);
		frame.setVisible(true);
	}

	public boolean login(String email, String password) {
		ConnectionConfiguration config = new ConnectionConfiguration(
				"talk.google.com", 5222, "gmail.com");
		connection = new XMPPConnection(config);
		try {
			System.out.println("Trying to connect...");
			connection.connect();
			System.out.println("Trying to login...");
			connection.login(email, password);
			System.out.println("   Login successful? "
					+ connection.isAuthenticated());
			BuddyListWindow window = new BuddyListWindow(connection, this);
			return true;
		} catch (XMPPException e1) {
			System.out.println("Error connecting");
			return false;
		}

	}

	public void setBuddy(String buddy){
		this.buddy = buddy;
	}
	
	public String getBuddy(){
		return this.buddy;
	}
	public XMPPConnection getConnection() {
		return this.connection;
	}

	public ArrayList<RosterEntry> getRoster() {
		roster = connection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		for (RosterEntry entry : entries) {
			list.add(entry);
		}
		return list;
	}

	public void sendMessage(String text) {
		ChatManager chatmanager = connection.getChatManager();
		Chat newChat = chatmanager.createChat(buddy, new MessageListener(){
			public void processMessage(Chat chat, Message message){
				System.out.println("Received message: " + message);
				message.getBody();
				
				}

		});
		try {
			newChat.sendMessage(text);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
