import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPException;

public class AddRemoveWindow extends JFrame {
	private JLabel labelEmail;
	private JLabel labelName;
	private JTextField fieldEmail;
	private JTextField fieldName;
	private JButton buttonAdd;
	private JButton buttonRemove;
	private JLabel status;
	private static JFrame frame;
	private String email;
	private String password;
	private ChatApp app;
	private Roster roster;

	public AddRemoveWindow(Roster roster) {

		this.roster = roster;
		setLayout(new FlowLayout());
		setSize(300, 200);
		setTitle("Add or Remove A Buddy");
		labelEmail = new JLabel("Enter Email: ");
		labelName = new JLabel("Enter Name: ");
		status = new JLabel("Waiting for input...");
		add(labelEmail);
		fieldEmail = new JTextField(20);
		add(fieldEmail);
		add(labelName);
		fieldName = new JTextField(20);
		add(fieldName);

		buttonAdd = new JButton("Add Buddy");
		buttonRemove = new JButton("Remove Buddy");
		add(buttonAdd);
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleAddBuddy(evt);
			}
		});

		add(buttonRemove);
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				handleRemoveBuddy(evt);
			}
		});

		add(status);
	}

	private void handleAddBuddy(ActionEvent evt) {
		try {
			roster.createEntry(fieldEmail.getText(), fieldName.getText(), null);
			status.setText("You added: " + fieldEmail.getText() + ", " +fieldName.getText());
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	private void handleRemoveBuddy(ActionEvent evt) {
		if (roster.contains(fieldEmail.getText())) {
			try {
				status.setText("You removed: " + fieldEmail.getText());
				roster.removeEntry(roster.getEntry(fieldEmail.getText()));
				
				
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
		else{
			status.setText(fieldEmail.getText() + " is not one of your buddies.");
			repaint();
		}
	}
}
