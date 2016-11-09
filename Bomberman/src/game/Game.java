package game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import chatclient.ChatClient;
import chatserver.ChatServer;
import constants.Constants;
import player.Player;
import ui.GameFrame;
import ui.MenuFrame;

public class Game implements WindowListener{
	
	/* UI components */
	private MenuFrame menuFrame;
	private GameFrame gameFrame;
	
	/* Chat components */
	private ChatClient chatClient;
	private ChatServer chatServer;
	
	/* Player info */
	Player player;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Game();
	}
	
	public Game(){
		openMenuWindow();
	}

	public void join(int tcp_port, String username, String ip) {
		chatClient = new ChatClient(ip, Constants.TCP_PORT, username, this);
		
		if(chatClient.connect()){
			player = new Player(username, Player.CLIENT);
			destroyMenuWindow();
			openGameWindow();
			chatClient.run();
		}else{
			chatClient = null;
		}
	}

	public void hostGame(int tcp_port, String username) {
		chatServer = new ChatServer(tcp_port, username, this);
		if(chatServer.host()){
			player = new Player(username, Player.HOST);
			destroyMenuWindow();
			openGameWindow();
			chatServer.run();
		}
		else{
			chatServer = null;
			dialogInMenu("Cannot host game!");
		}
	}

	public void dialogInMenu(String string) {
		if(menuFrame != null)
			JOptionPane.showMessageDialog(menuFrame, string);
	}
	public void dialogInGame(String string) {
		if(gameFrame != null)
			JOptionPane.showMessageDialog(gameFrame, string);
	}

	public void sendMessage(String message) {
		if(player.isHost()){
			if(message.equals(Constants.LEAVE_GAME))
				chatServer.broadcast(message);
			else
				chatServer.sendMessage(message);
		}
		else{
			chatClient.sendMessage(message);
		}
	}

	public String getPlayerName() {
		return (player == null)? null:player.getUsername();
	}

	public void receiveMessage(String messageReceived) {
		// TODO Auto-generated method stub
		gameFrame.getChatPanel().updateChat(messageReceived);
	}

	public void receiveMessage(String username, String messageReceived) {
		// TODO Auto-generated method stub
		gameFrame.getChatPanel().updateChat("("+username + "): " + messageReceived);
	}

	private boolean quit(){
		int response = JOptionPane.showConfirmDialog(menuFrame, "Are you sure you want to leave?");
		return (response == JOptionPane.YES_OPTION);
	}
	public void leave() {
		if(quit()){
			sendMessage(Constants.LEAVE_GAME);
			destroyGameWindow();
			openMenuWindow();
		}
		closeSockets();
	}
	
	private void openMenuWindow(){
		menuFrame = new MenuFrame(this);
		menuFrame.setVisible(true);
	}
	
	private void openGameWindow(){
		gameFrame = new GameFrame(this);
		gameFrame.setVisible(true);
		gameFrame.addWindowListener(this);
	}
	
	private void destroyMenuWindow(){
		menuFrame.setVisible(false);
		menuFrame.dispose();
	}
	
	private void destroyGameWindow(){
		gameFrame.setVisible(false);
		gameFrame.dispose();
	}
	
	public void disconnection(String message) {
		dialogInGame(message);
		destroyGameWindow();
		openMenuWindow();
	}
	
	private void closeSockets() {
		if(player.isHost()){
			chatServer.stop();
		}
		else{
			chatClient.close(1);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(quit()){
			closeSockets();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
