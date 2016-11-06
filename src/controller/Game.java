package controller;

import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import chatclient.ChatClient;
import chatserver.ChatServer;
import constants.Constants;
import player.Player;
import ui.ChatPanel;
import ui.GameFrame;
import ui.MenuFrame;

public class Game {
	private Player player;
	
	private MenuFrame menuFrame;
	private GameFrame gameFrame;
	private ChatPanel chatPanel;
	
	private ChatServer chatServer;
	private ChatClient chatClient;
	
	public static void main(String[] args) {
		Game game = new Game();
		
	}

	public Game(){
		menuFrame = new MenuFrame(this);
		menuFrame.setVisible(true);
	}

	public void hostGame(int tcpPort, String username) {
		chatServer = new ChatServer(tcpPort, username, this);
		
		if(chatServer.host()){
			player = new Player(username, Player.HOST);
			menuFrame.setVisible(false);
			menuFrame.dispose();
			
			gameFrame = new GameFrame(this);
			gameFrame.setVisible(true);
			chatPanel = gameFrame.getChatPanel();
			chatServer.run();
		}
		else{
			chatServer = null;
			dialogInMenu("Cannot host game!");
		}
	}
	public void join(int tcpPort, String username, String ip) {
		chatClient = new ChatClient(ip, Constants.TCP_PORT, username, this);
		
		if(chatClient.connect()){
			player = new Player(username, Player.CLIENT);
			menuFrame.setVisible(false);
			menuFrame.dispose();
			
			gameFrame = new GameFrame(this);
			gameFrame.setVisible(true);
			chatClient.run();
		}else{
			chatClient = null;
		}
		
	}

	public void sendMessage(String text) {
		if(player.isHost()){
			System.out.println("Sent from server");
			chatServer.sendMessage(text);
		}
		else{
			System.out.println("Sent from client");
			chatClient.sendMessage(text);
		}
	}

	public String getPlayerName() {
		return (player == null? null: player.getUsername());
	}

	public void dialogInMenu(String string) {
		JOptionPane.showMessageDialog(menuFrame, string);
	}

	public void receiveMessage(String username, String msg) {
		chatPanel.updateChat("("+username+"): " + msg);
	}

	public void receiveMessage(String messageReceived) {
		chatPanel.updateChat(messageReceived);
		
	}

	
	
}
