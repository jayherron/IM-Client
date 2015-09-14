
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPException;

public class Gui {
	// Swing fields for Login Pane
	private JFrame frame;
	private JLabel nameLabel;
	private JLabel passLabel;
	private JTextField nameTF;
	private JPasswordField passTF;
	private JButton submitBtn;
	private JTextField outputTF;
	private boolean chatStarted;

	// Swing Fields for message Pane
	private JFrame frame2;
	private JButton remove;
	private JButton add;
	private  String username; 
	private String friendName;
	private JTextField chatSpace;
	private JButton StartChat;
	private JButton statusButton;
	private JLabel console;
	private JLabel status;

	private static Connect connect;
	private JScrollPane buddyList; 
	// other fields are next

	private JList budList; 
	private JList messages;
	private ArrayList<String> test;
	private ArrayList<String> pastChats;
	private static User a;
	private JScrollPane messageArea;


	public void updateMessages(String text) {
		pastChats.add(friendName.substring(0,friendName.indexOf("@"))+": "+text);
		String[] data3 = (String[]) pastChats.toArray(new String[pastChats.size() ] );
		messages.setListData(data3);
	}
	
	public void updateBuddyList(){
		test = a.getBuddyList();
		String[] data3 = (String[]) test.toArray(new String[test.size() ] );
		budList.setListData(data3);
	}
	
	public void updateBuddySelect(String text){
		status.setText(text);
	}

	// --------------------------------------------------- main() ---
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Gui theApp = new Gui(); // our GUI application
				connect = new Connect(theApp);
			}
		});
	}

	// --------------------------------------------------- constructor ---
	public Gui() {
		// we could do other non-GUI work here if needed
		initialize(); // our method to setup the GUI
	}

	// --------------------------------------------------- initialize() ---
	private void initialize() {

		// Frame for Chat
		frame2 = new JFrame();
		frame2.setSize(500,600);
		frame2.setTitle("Chatter Box");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setLayout(new FlowLayout());
		//frame2.setLayout(new BorderLayout());
		frame2.setVisible(false);

		//Frame for Login
		frame = new JFrame();
		frame.setSize(400,300);
		frame.setTitle("Login Window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());

		//Stuff for Login Field
		nameLabel = new JLabel("Enter Username: ");
		frame.add(nameLabel);
		nameTF = new JTextField(10);
		frame.add(nameTF);
		passLabel = new JLabel("  Enter Password: ");
		frame.add(passLabel);
		passTF = new JPasswordField(20);
		frame.add(passTF);
		submitBtn = new JButton("Login");
		frame.add(submitBtn);
		submitBtn.addActionListener( new HandleSubmitPressed() );
		outputTF = new JTextField(15);
		frame.add(outputTF);
		frame.pack();  // uncomment this to shrink frame around contents
		frame.setVisible(true); // necessary to start event-handling thread

		//Stuff for ChatField
		StartChat = new JButton("StartChat");
		frame2.add(StartChat);
		StartChat.addActionListener(new HandleStartPressed());
		remove = new JButton("Remove");
		frame2.add(remove);
		remove.addActionListener(new HandleRemovePressed() );
		add = new JButton("Add");
		frame2.add(add);
		add.addActionListener(new HandleAddPressed() );
		statusButton = new JButton("Set Status");
		frame2.add(statusButton);
		statusButton.addActionListener(new HandleSatusPressed() );

		chatSpace = new JTextField(20);
		frame2.add(chatSpace);
		chatSpace.addActionListener(new ActionListener(){  //sends message when enter is pressed
			public void actionPerformed(ActionEvent e){
				if (chatStarted){ 
					String msg = chatSpace.getText().trim();
				
				connect.sendMSG(msg);
				pastChats.add("you: " + msg);
				String[] data3 = (String[]) pastChats.toArray(new String[test.size() ] );
				messages.setListData(data3);
				chatSpace.setText(" ");
				}
			}});

		console = new JLabel("Welcome to our program");
		frame2.add(console);
		
	
		String[] data = {" ", " "}; //temporary values to make the computer happy
		pastChats = new ArrayList<String>();

		budList = new JList(data);
		buddyList = new JScrollPane(budList);
		budList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//budList.setPreferredSize(new Dimension(100, 200));
		frame2.add(buddyList);

		messages = new JList(data);
		messageArea = new JScrollPane(messages);
		messageArea.setPreferredSize(new Dimension(200, 200));
		frame2.add(messageArea);

		status = new JLabel("Select buddy to see status and start chat");
		frame2.add(status);

		budList.addListSelectionListener(  new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				boolean adjust = listSelectionEvent.getValueIsAdjusting();
				if (!adjust) {
					if (! (budList.getSelectedValue() == null)) {
						String name = (String) budList.getSelectedValue();
						connect.getBudStatus(name);
					}
				}
			}
		});

	}
	//---------------------------Event Handlers-------------------------------
	class HandleSubmitPressed implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Logging In");
			username = nameTF.getText().trim();     //comment out to hardcode login
			char[] passwd = passTF.getPassword();
			String password = new String(passwd);

			//String password = "softwaredevelopment";       // hardcoded login
			//username = "cs2110test@gmail.com";

			if ( username.equals("") || password.equals("")) {
				outputTF.setText("you must fill in fields");
			}
			else {
				if (connect.login(username, password)) {
					a = new User(username);
					outputTF.setText("Hello! Login succes!");
					connect.getBuddies(a);
					updateBuddyList();
					frame.dispose();
					frame2.setVisible(true);
				}
				else {
					outputTF.setText("Login Failed");
				}
			}
			nameTF.setText("");
			passTF.setText("");
			frame.repaint();      
		}
	}

	class HandleStartPressed implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String friendName2 = chatSpace.getText().trim();
			if (friendName2.contains("@")){
				chatStarted = true;
				System.out.println(friendName2);
				connect.startChat(friendName2);    
				console.setText("Starting chat with " + friendName2);
				chatSpace.setText(" ");
			}
			else if ( ! budList.isSelectionEmpty() ) {
				friendName = (String) budList.getSelectedValue(); 
				if (friendName.contains("@")) {
					chatStarted = true;				
					connect.startChat(friendName);    
					console.setText("Starting chat with " + friendName);
				}
				else {
					console.setText(friendName + " is not valid");
				}
			}
			else {
				console.setText("Please choose a buddy");
			}
		}
	}

	class HandleAddPressed implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String friendname = chatSpace.getText().trim();
			System.out.println(friendname);
			if (! friendname.equals("")) {
				if (! connect.addBuddy(a, friendname)){
					console.setText("Failed to add Buddy");
				}
				else console.setText(friendname + " has been added to your buddys");
				updateBuddyList();
			}
			else {
				console.setText("Enter a buddy to add");	
			}
		}
	}

	class HandleRemovePressed implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			//String friendName = chatSpace.getText().trim();
			if ( ! budList.isSelectionEmpty() ) {
				String friendName = (String) budList.getSelectedValue();
				connect.removeBuddy(a, friendName);
				updateBuddyList();
				console.setText(friendName + " has been removed from your buddys");
			}
			else {
				console.setText("Please select a buddy");
			}
		}
	}
	class HandleSatusPressed implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String status = chatSpace.getText().trim();
			if (status.equals("")) {
				console.setText("Enter a Status");
			}
			else {
			connect.setStatus(status);
			console.setText("your status: " + status);
			chatSpace.setText("");
			}
		}
	}
}
