package gameserver;

import java.net.DatagramSocket;

public class GameBroadcaster extends Thread {
	

	
	private GameServer gameServer;
	private DatagramSocket serverSocket;
	private boolean gameOver;

	public GameBroadcaster(GameServer gameServer) {
		this.gameOver = false;
		this.gameServer = gameServer;
		this.serverSocket = gameServer.getServerSocket();
	}

	@Override
	public void run(){
		// send initial
	}
}
