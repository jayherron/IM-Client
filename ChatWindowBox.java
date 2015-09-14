import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;


public class ChatWindowBox extends JFrame {

	private JFrame frame;
	private JTextField textField_2;
	private ChatApp app;
	public String buddyLabel;
	public JLabel chatWithLabel;
	public XMPPConnection conn;
	private JButton btnSend;

	private JTextArea textArea;

	public ChatWindow(final ChatApp app, XMPPConnection conn) {
		this.app = app;
		this.conn = conn;

		setBounds(100, 100, 450, 437);
		//setDefaultCloseOperation(setVisible(false));
		getContentPane().setLayout(null);

		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(12, 320, 83, 14);
		getContentPane().add(lblMessage);

		textField_2 = new JTextField();
		textField_2.setBounds(10, 340, 327, 34);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		entList e = new entList();
		textField_2.addKeyListener(e);

		btnSend = new JButton("Send");


		btnSend.setBounds(341, 340, 91, 34);
		getContentPane().add(btnSend);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 25, 422, 289);
		getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		JButton btnClearHistory = new JButton("Clear History");
		btnClearHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(null);
			}
		});
		btnClearHistory.setBounds(150, 380, 126, 23);
		getContentPane().add(btnClearHistory);

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Toolkit.getDefaultToolkit().beep();
				app.sendMessage(textField_2.getText());
				textArea.append("[Me]: " + textField_2.getText() + "\n");
				textField_2.setText(null);
				repaint();

			}
		});

		PacketListener listener = new PacketListener(){
			public void processPacket(Packet p){
				if (p instanceof Message){
					Message msg = (Message) p;
					textArea.append("["+getTitle()+"]" + ": " + msg.getBody() + "\n");
				}
			}
		};
		conn.addPacketListener(listener,null);


	}
	private class entList implements KeyListener {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER)
				btnSend.doClick();
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}


}


}
