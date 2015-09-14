import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
                               
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;



public class MainGUI {

	private JLabel label1;
	private JLabel label2;
	private JTextField field;
	private JPasswordField field2;
	private JButton button;
	private JTextField loginStatus;
	private JFrame frame;
	private String email;
	private String password;
	
	ConnectionConfiguration config = new ConnectionConfiguration(
			"talk.google.com", 5222, "gmail.com");
	XMPPConnection connection = new XMPPConnection(config);
	
	public XMPPConnection getConnection(){
		return connection;
	}

	public void start() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainGUI window = new MainGUI();
				window.frame.setVisible(true);
			}
		});
		System.out.println("main() method exiting!");

	}

	public MainGUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(400, 200);
		frame.setTitle("Instant Messaging");
		label1 = new JLabel("Enter Username: ");
		label2 = new JLabel("Enter Password: ");
		loginStatus = new JTextField(30);
		loginStatus.setEditable(false);
		loginStatus.setText("Waiting for input...");
		frame.add(label1);
		field = new JTextField(20);
		frame.add(field);
		frame.add(label2);
		field2 = new JPasswordField(20);
		frame.add(field2);
		button = new JButton("Login");
		button.addActionListener(new LoginButtonListener());
		frame.add(button);
		frame.add(loginStatus);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	class LoginButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			try {
				System.out.println("Trying to connect...");
				connection.connect();
				System.out.println("Trying to login...");
				email = field.getText();
				password = field2.getText();
				connection.login(email, password);
				System.out.println("   Login successful? "
						+ connection.isAuthenticated());
				if (connection.isAuthenticated()) {
					frame.setVisible(false);
					//ChatWindow window = new ChatWindow(MainGUI.this);
					//window.start();
				}

			} catch (XMPPException e1) {
				loginStatus.setText("That username-password combination is incorrect");
				frame.repaint();
				System.out.println("login unsuccessful");
				// e1.printStackTrace();
			}

		}
		
		

	
}
	public String getEmail(){
		return this.email;

}
}
