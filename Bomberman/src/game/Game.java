package game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import chatclient.ChatClient;
import chatserver.ChatServer;
import constants.Constants;
import gameclient.GameClient;
import gameserver.GameServer;
import gameserver.GameState;
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
	
	/* Game Connection components */
	private GameServer gameServer;
	private GameClient gameClient;
	
	/* Player info */
	Player player;
	private String serverIp;
	//main function method
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Game();
	}
	//game class constructor
	public Game(){
		openMenuWindow();
	}
	//method for a client to join hosted game
	public void join(int tcp_port, String username, String ip) {
		
		chatClient = new ChatClient(ip, Constants.TCP_PORT, username, this);
		this.setServerIp(ip);
		gameClient = new GameClient(this, username);
		
		if(chatClient.connect() && gameClient.connect()){
			player = new Player(username, Player.CLIENT);
			destroyMenuWindow();
			openGameWindow();
			chatClient.start();
			gameClient.start();
		}else{
			chatClient = null;
			gameClient = null;
		}
	}
	//method if user chooses to host a game
	public void hostGame(int tcp_port, String username) {
		
		chatServer = new ChatServer(tcp_port, username, this);
		gameServer = new GameServer(this);
		if(chatServer.host() && gameServer.host()){
			player = new Player(username, Player.HOST);
			destroyMenuWindow();
			openGameWindow();
			chatServer.start();
			gameServer.startServing();
		}
		else{
			chatServer = null;
			gameServer = null;
		}
	}
	//method for showing in menu dialog
	public void dialogInMenu(String string) {
		if(menuFrame != null)
			JOptionPane.showMessageDialog(menuFrame, string);
	}
	//method for showing in game dialog
	public void dialogInGame(String string) {
		if(gameFrame != null)
			JOptionPane.showMessageDialog(gameFrame, string);
	}
	//method for sending messages through the chat system
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
	//method for retrieving a certain player name
	public String getPlayerName() {
		return (player == null)? null:player.getUsername();
	}
	//method for retrieving server ip address
	public String getServerIp() {
		return (serverIp == null)? null:serverIp;
	}
	//method to set server ip address
	public void setServerIp(String ip) {
		this.serverIp =ip;
	}
	//method to handle updating chat
	public void receiveMessage(String messageReceived) {
		// TODO Auto-generated method stub
		gameFrame.getChatPanel().updateChat(messageReceived);
	}
	//method to handle updating chat
	public void receiveMessage(String username, String messageReceived) {
		// TODO Auto-generated method stub
		gameFrame.getChatPanel().updateChat("( "+username + " ): " + messageReceived);
	}
	//method that asks if client wants to quit the game
	private boolean quit(){
		int response = JOptionPane.showConfirmDialog(menuFrame, "Are you sure you want to leave?");
		return (response == JOptionPane.YES_OPTION);
	}
	//method if client chooses to quit the hosted game
	public void leave() {
		if(quit()){
			sendMessage(Constants.LEAVE_GAME);
			destroyGameWindow();
			if(player.isHost())
				closeSockets();
			System.exit(0);
		}
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
			gameServer.stop();
		}
		else{
			chatClient.close(1);
			gameClient.close();
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(quit()){
			System.exit(0);
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

	public void makeReady() {
		if(!player.isHost()){
			gameClient.makeReady();
		}
	}

	public void removeGameClient(String username) {
		gameServer.removePlayer(username);
		
	}

	public Player getPlayer() {
		return this.player;
	}

	public void start() {
		if(player.isHost()){
			if(gameServer.isOkayToStart()){
				dialogInGame("Starting!");
				this.chatServer.stopAccepting();
				gameServer.broadcast("STARTNA");
				gameFrame.startGame(); // for host
				gameServer.startGame(); // for others, broadcast
			}
		}
		else{
			// for client side only
			gameFrame.startGame();
		}
		
	}

	public void announce(String string) {
		gameFrame.getChatPanel().updateChat(string);
		chatServer.broadcast(string);
	}

	public void sendGameAction(String action) {
		if(!player.isHost()){
			gameClient.sendGameAction(action);
		}
		else{
			// update game
			gameServer.doAction(action);
		}
	}
	
	public GameServer getGameServer() {
		return this.gameServer;
	}

	public GameClient getGameClient() {
		return this.gameClient;
	}
	
	public GameFrame getGameFrame(){
		return this.gameFrame;
	}

	public void resetPlayers() {
		gameServer.broadcast("RESET_PLAYERS");
	}

	public void updatePlayers() {
		gameServer.broadcast("UPDATE~"+ gameServer.getGameState() .toString());
	}

	
}
