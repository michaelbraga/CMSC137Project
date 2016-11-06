package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Game;

public class ChatPanel extends JPanel implements ActionListener{
	private final static String nl = "\n";
	
	private Game game;
	private JButton leaveButton;
	private JTextArea chatTextArea;
	private JTextArea messageField;
	private JButton sendButton;
	

	public ChatPanel(Game game, Dimension d){
		super();
		this.game = game;
		this.setMaximumSize(d);
		this.setLayout(new GridBagLayout());
		initComponents();
	}
	
	private void initComponents(){
		initLeaveButton();
		initChatArea();
		initInputArea();
		addButtonListeners();
	}

	private void initLeaveButton(){
		leaveButton = new JButton("Leave Game");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0/12.0;
		gbc.gridwidth = 2;
		this.add(leaveButton, gbc);
	}
	
	private void initChatArea(){
		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		GridBagConstraints gbc = new GridBagConstraints();
		JScrollPane scroll2 = new JScrollPane(chatTextArea, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setBorder(BorderFactory.createCompoundBorder(
		        scroll2.getBorder(), 
		        BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 9.5/12.0;
		gbc.gridwidth = 2;
		this.add(scroll2, gbc);
	}
	
	private void initInputArea(){
		messageField = new JTextArea();
		sendButton = new JButton("Send");
		GridBagConstraints gbc = new GridBagConstraints();
		JScrollPane scroll1 = new JScrollPane(messageField, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll1.setBorder(BorderFactory.createCompoundBorder(
		        scroll1.getBorder(), 
		        BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.5/12.0;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 9.0/10.0;
		this.add(scroll1, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1.0/10.0;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(sendButton, gbc);
	}
	
	private void addButtonListeners() {
		leaveButton.setActionCommand("leave");
		leaveButton.addActionListener(this);
		sendButton.setActionCommand("send");
		sendButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "leave": 
				updateChat("Leave"); 
				break;
			case "send": 
				sendMessage(messageField.getText());
				break;
		}
	}

	private void sendMessage(String text) {
		if(!text.trim().isEmpty() && !text.isEmpty()){
			game.sendMessage(text);
			updateChat("("+game.getPlayerName() + "): " + text);
			messageField.setText("");
		}
	}

	public void updateChat(String string) {
		this.chatTextArea.append(string + nl);
	}

}
