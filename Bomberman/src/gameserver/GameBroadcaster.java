package gameserver;

import java.net.DatagramSocket;

public class GameBroadcaster extends Thread {
	

	
	private GameServer gameServer;
	private DatagramSocket serverSocket;
	private boolean gameOver;
	//game broadcaster class constructor
	public GameBroadcaster(GameServer gameServer) {
		this.gameOver = false;
		this.gameServer = gameServer;
		this.serverSocket = gameServer.getServerSocket();
	}
	//method for thread execution
	@Override
	public void run(){
		
	}
}
