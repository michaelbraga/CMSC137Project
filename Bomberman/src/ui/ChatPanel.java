package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import constants.Constants;
import game.Game;

public class ChatPanel extends JPanel implements ActionListener{
	private final static String nl = "\n";
	
	private Game game;
	private JButton leaveButton;
	private JTextArea chatTextArea;
	private JTextArea messageField;
	private JButton sendButton;
	
	//chat panel class constructor
	public ChatPanel(Game game, Dimension d){
		super();
		this.game = game;
		this.setMaximumSize(d);
		this.setLayout(new GridBagLayout());
		initComponents();
	}
	//method for initializing chat panel ui components
	private void initComponents(){
		initLeaveButton();
		initChatArea();
		initInputArea();
		addButtonListeners();
	}
	//method for initializing leave button
	private void initLeaveButton(){
		leaveButton = new JButton("LEAVE GAME");
		leaveButton.setFont(new Font(Constants.FONT_STYLE, Font.PLAIN, 12));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 1.0/12.0;
		gbc.gridwidth = 2;
		this.add(leaveButton, gbc);
	}
	//method for rendering initial chat area ui
	private void initChatArea(){
		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		chatTextArea.setFont(new Font(Constants.FONT_STYLE, Font.PLAIN, 12));
		GridBagConstraints gbc = new GridBagConstraints();
		JScrollPane scroll2 = new JScrollPane(chatTextArea, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
	//method for initializing input area 
	private void initInputArea(){
		messageField = new JTextArea();
		messageField.setFont(new Font(Constants.FONT_STYLE, Font.PLAIN, 13));
		sendButton = new JButton("SEND");
		sendButton.setFont(new Font(Constants.FONT_STYLE, Font.BOLD, 13));
		GridBagConstraints gbc = new GridBagConstraints();
		JScrollPane scroll1 = new JScrollPane(messageField, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
	//method for adding button listeners for chat system ui
	private void addButtonListeners() {
		leaveButton.setActionCommand("leave");
		leaveButton.addActionListener(this);
		sendButton.setActionCommand("send");
		sendButton.addActionListener(this);
	}
	//method when client has done an action
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "leave": 
				leaveGame(); 
				break;
			case "send": 
				sendMessage(messageField.getText());
				break;
		}
	}
	//method for leaving the game
	private void leaveGame() {
		game.leave();
	}
	//method for sending ui message to the game class for broadcast
	private void sendMessage(String text) {
		if(!text.trim().isEmpty() && !text.isEmpty()){
			game.sendMessage(text.trim());
			updateChat("[ "+game.getPlayerName() + " ]: " + text.trim());
			messageField.setText("");
		}
	}
	//method for updating chat messages
	public void updateChat(String string) {
		this.chatTextArea.append(string + nl);
	}

}
