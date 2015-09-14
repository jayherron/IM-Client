import java.util.ArrayList;


public class User { 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String username;
	public String password;
	public String status;
	public ArrayList<String> buddyList = new ArrayList<String>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<String> getBuddyList() {
		return buddyList;
	}
	public void setBuddyList(ArrayList<String> buddyList) {
		this.buddyList = buddyList;
	}
	
	public void getBuddies() {
		
	}
	
	public void addBuddy(String friendName) {
		buddyList.add(friendName);
	}
	public void removeBuddy(String friendName) {
		buddyList.remove(friendName);
	}
		
	public User(String username) {
		super();
		this.username = username;
	}
}
