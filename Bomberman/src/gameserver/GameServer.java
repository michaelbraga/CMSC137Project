package gameserver;

import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import constants.Constants;
import game.Game;

public class GameServer{
	
	private ArrayList<Client> players;
	private boolean gameOver;
	private PlayerListener playerListener = null;
	DatagramSocket serverSocket = null;
	private int gameStatus = Constants.WAITING_FOR_PLAYERS;
	private Game game;
	private ArrayList<Point> spawnPoints;
	private GameBroadcaster gameBroadcaster;
	private GameState gameState;
	

	public GameServer(Game game){
		this.game = game;
		gameOver = false;
		players =  new ArrayList<>();
	}
	
	public boolean host(){
		try {
			serverSocket = new DatagramSocket(Constants.UDP_PORT);
			serverSocket.setSoTimeout(2000);
			System.out.println("*Game server is now listening at port: " + Constants.UDP_PORT);
		} catch (SocketException e) {
			serverSocket = null;
			e.printStackTrace();
			game.dialogInMenu("Cannot start Game server!");
			return false;
		}
		return true;
	}
	
	public void startServing(){
		if(serverSocket != null){
			playerListener = new PlayerListener(this);
			playerListener.start();
			initializeSpawnPoints();
			gameBroadcaster = new GameBroadcaster(this);
		}
	}

	private void initializeSpawnPoints() {
		/*
	    -------------------
		|A               B|
		|                 |
		|     C     D     |
		|                 |
		|E               F|
		-------------------
		 A - (1,1)
		 B - (13, 1)
		 C - (5, 6)
		 D - (9, 6)
		 E - (1, 11)
		 F - (13, 11)
		 
		 */
		spawnPoints = new ArrayList<>();
		spawnPoints.add(new Point(1,1));
		spawnPoints.add(new Point(13, 1));
		spawnPoints.add(new Point(5, 6));
		spawnPoints.add(new Point(9, 6));
		spawnPoints.add(new Point(1, 11));
		spawnPoints.add(new Point(13, 11));
	}

	public void addPlayer(Client p){
		boolean existing = false;
		for(int i=0; i<players.size(); i+=1){
			String ad = players.get(i).getAddress().toString();
			String un = players.get(i).getUsername().toString();
			int po = players.get(i).getPort();
			if(ad.equals(p.getAddress().toString()) && un.equals(p.getUsername()) && po==p.getPort()){
				existing = true;
				break;
			}
		}
		if(!existing){
			System.out.println("Added: "+p.getUsername());
			p.assignSpawnPosition(spawnPoints.get(players.size()));
			this.players.add(p);
		} 
	}

	public ArrayList<Client> getPlayers(){
		return this.players;
	}

	public DatagramSocket getServerSocket() {
		// TODO Auto-generated method stub
		return this.serverSocket;
	}

	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return this.gameOver;
	}

	public int getGameStatus() {
		return this.gameStatus;
	}

	public void stop() {
		// TODO Auto-generated method stub
		try{
			playerListener.stop();
			serverSocket.close();
			System.out.println("*Game server socket at "+ Constants.UDP_PORT +" is now close");
		}
		catch(Exception e){
			System.out.println("*Cannot close sockets!");
			e.printStackTrace();
		}
	}

	public void send(String message, Client player) {
		// TODO Auto-generated method stub
				byte[] letter = new byte[256];
				letter = new String(message.toString()).getBytes();
				DatagramPacket packet;
				try {
					packet = new DatagramPacket(letter, letter.length, player.getAddress(), player.getPort());
					serverSocket.send(packet);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
	}

	public void makePlayerReady(String username) {
		for(Client c: players){
			if(c.getUsername().equals(username)){
				c.makeReady();
				game.announce("( "+c.getUsername() + " ) is ready to play!");
			}
		}
	}

	public void m(String string) {
		game.dialogInGame(string);
	}

	public void send(String message, String username) {
		for(Client c: players){
			if(c.getUsername().equals(username)){
				send(message, c);
			}
		}
	}

	public void removePlayer(String username) {
		for(int i=0; i<players.size(); i+=1){
			if(players.get(i).getUsername().equals(username)){
				players.remove(i);
				game.dialogInGame(username + " has been disconnected! " + players.size());
				break;
			}
		}
	}

	public void broadcast(String message) {
		for(Client c: players){
			send(message, c);
		}
	}

	public boolean isOkayToStart() {
		if(players.isEmpty()){
			game.dialogInGame("You can't play with yourself!");
			return false;
		}
			
		for(Client c: players){
			if(!c.isReady()){
				game.dialogInGame("Some players are not ready!");
				return false;
			}
		}
		return true;
	}

	public void startGame() {
		gameBroadcaster.start();
	}
}
