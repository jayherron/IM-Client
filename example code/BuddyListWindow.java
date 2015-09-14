import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BuddyListWindow {

	public ChatApp app;
	public JButton submitButton;
	public JTextField passwordField;
	public JLabel label1;
	public JLabel statusLabel;

	private JFrame mainFrame;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;

	private JList buddyJList;

	private JTextField statusTextField;
	private JLabel buddyLabel;
	private JLabel addRemoveLabel;
	private JLabel status;

	private JButton enterButton;
	private JButton addButton;
	private JButton removeButton;
	private JButton addRemoveButton;
	private JButton updateButton;
	private JButton busyButton;

	
	private JRadioButton availableButton;
	private JRadioButton awayButton;
	private JRadioButton invisibleButton;
	private ButtonGroup group;
	private String available = "Available";
	private String away = "Away";
	private String invisible = "invisible";

	private XMPPConnection conn;
	private Roster roster;
	public static Chat newChat;
	public RosterEntry selectedBuddy;
	private Presence presence;

	public BuddyListWindow(XMPPConnection conn, ChatApp app) {
		super();
		roster = conn.getRoster();
		initialize();
		mainFrame.setVisible(true);
		this.app = app;
		this.conn = conn;
	}

	private void initialize() {

		this.mainFrame = new JFrame();

		this.mainFrame.setTitle("Buddy List");
		this.mainFrame.setSize(300, 800);
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buddyLabel = new JLabel("Select a buddy to chat with, then hit Chat.");
		statusLabel = new JLabel("Choose a buddy.");
		status = new JLabel("Enter a status...");
		statusTextField = new JTextField(30);
		statusTextField.setText("Enter a status here, and click Update.");

		roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
		buddyJList = new JList(roster.getEntries().toArray());
		buddyJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		buddyJList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleBuddyListSelect(e);
			}
		});
		
		
		RadioListener myListener = new RadioListener();
		a
				
			}
		});
		invisibleButton = new JRadioButton(invisible);
		invisibleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt){
				
			}
		});
		
		group = new ButtonGroup();
		group.add(availableButton);
		group.add(awayButton);
		group.add(invisibleButton);
		

		enterButton = new JButton("Chat");
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
			}
		});

		addRemoveButton = new JButton("Add or Remove a Buddy");
		addRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				AddRemoveWindow window = new AddRemoveWindow(roster);
				window.setVisible(true);

			}

		});
		this.mainFrame.repaint();

		updateButton = new JButton("Update Status");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleUpdateStatusAvailable(evt);
			}
		});

		createLayout();

	}

	private void createLayout() {

		panel1 = new JPanel();
		panel1.setSize(150, 500);
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel1.setBorder(BorderFactory.createTitledBorder("Buddy List:"));

		panel1.add(buddyLabel);
		panel1.add(new JScrollPane(buddyJList));
		panel1.add(enterButton);
		this.mainFrame.add(BorderLayout.NORTH, panel1);

		panel3 = new JPanel();
		// panel3.add(statusLabel);
		panel3.add(addRemoveButton);
		panel3.add(statusTextField);
		panel3.add(updateButton);
		panel3.add(status);
		panel3.add(availableButton);
		panel3.add(awayButton);
		panel3.add(invisibleButton);
		this.mainFrame.add(BorderLayout.SOUTH, panel3);

		this.mainFrame.pack();
	}

	protected void handleEnterPressed(ActionEvent evt) {
		if (selectedBuddy == null)
			statusLabel.setText("STOP: select a buddy before hitting Chat!");
		else {
			statusLabel.setText("You hit Chat and selected buddy: "
					+ selectedBuddy);
			this.mainFrame.repaint();

			ChatWindow chatWindow = new ChatWindow(app, conn);
			chatWindow.setTitle(selectedBuddy.getUser().toString());
			app.setBuddy(selectedBuddy.getUser().toString());
			chatWindow.setVisible(true);
		}
	}

	private void handleBuddyListSelect(ListSelectionEvent e) {
		selectedBuddy = (RosterEntry) buddyJList.getSelectedValue();
		statusLabel.setText("You've selected Buddy: " + selectedBuddy);
		this.mainFrame.repaint();
	}

	private void handleUpdateStatusAvailable(ActionEvent evt) {
		presence = new Presence(Presence.Type.available);
		presence.setMode(Presence.Mode.available);
		presence.setStatus(statusTextField.getText());
		conn.sendPacket(presence);
	}
	private void handleUpdateStatusAway(ActionEvent evt) {
		presence = new Presence(Presence.Type.available);
		presence.setMode(Presence.Mode.away);
		presence.setStatus(statusTextField.getText());
		conn.sendPacket(presence);
	}
	private void handleUpdateStatusInvisible(ActionEvent evt) {
		presence = new Presence(Presence.Type.available);
		presence.setMode(Presence.Mode.dnd);
		presence.setStatus(statusTextField.getText());
		conn.sendPacket(presence);
	}

	class RadioListener implements ActionListener{

		public void actionPerformed(ActionEvent evt) {
			if (evt.getActionCommand() == available) {
				handleUpdateStatusAvailable(evt);
			}
			if (evt.getActionCommand() == away){
				presence = new Presence(Presence.Type.available);
				presence.setMode(Presence.Mode.away);
				conn.sendPacket(presence);
			}
			if (evt.getActionCommand() == invisible ){
				handleUpdateStatusInvisible(evt);
			}
			
		}
		
	}

}
