import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.SpringLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ChatWindow extends JFrame {

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
	

