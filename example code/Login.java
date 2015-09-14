import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Login extends JFrame {
	private JLabel label1;
	private JLabel label2;
	private JTextField field;
	private JPasswordField field2;
	private JButton button;
	private JTextField loginStatus;
	private static JFrame frame;
	private String email;
	private String password;
	private ChatApp app;


	public Login(final ChatApp app) {

		this.app = app;
		setLayout(new FlowLayout());
		setSize(400, 200);
		setTitle("Instant Messaging");
		label1 = new JLabel("Enter Username: ");
		label2 = new JLabel("Enter Password: ");
		loginStatus = new JTextField(30);
		loginStatus.setEditable(false);
		loginStatus.setText("Waiting for input...");
		add(label1);
		field = new JTextField(20);
		add(field);
		add(label2);
		field2 = new JPasswordField(20);
		add(field2);
		entList e = new entList();
		field2.addKeyListener(e);

		button = new JButton("Login");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				handleLogin();
			}
		});
		add(button);
		add(loginStatus);	

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	protected void handleLogin() {
		String email = field.getText().trim();
		char[] pass = field2.getPassword();
		String password = new String(pass);
		if (app.login(email, password)) {
			this.setVisible(false);
			// ChatWindow window = new ChatWindow(app);
			// window.setVisible(true);
		} else {
			loginStatus.setText("Incorrect email-password combination");
			// mainApp.logMessage("LoginWindow: no user id entered.");
		}
		
	}

	private class entList implements KeyListener {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER)
				button.doClick();
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
		}
}