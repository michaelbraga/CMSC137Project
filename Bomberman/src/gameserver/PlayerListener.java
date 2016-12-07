package gameserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import constants.Constants;

public class PlayerListener extends Thread {
	private GameServer gameServer;
	private DatagramSocket serverSocket;
	private boolean gameOver;
	//player listener class constructor
	public PlayerListener(GameServer gs){
		this.gameServer = gs;
		this.serverSocket = gs.getServerSocket();
		this.gameOver = gs.isGameOver();
	}
	//client player listener class thread execution
	@Override
	public void run(){
		String messageReceived;
		while(!gameOver){
			
			try{
				byte[] buffer = new byte[256];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				serverSocket.receive(packet);
				messageReceived = new String(buffer).trim();
				// process received message
				switch(gameServer.getGameStatus()){
				
					case Constants.WAITING_FOR_PLAYERS:
						// adding players
						if(messageReceived.startsWith("PACONNECT+")){
							String tokens[] = messageReceived.split("\\+");
							System.out.println("Adding " + tokens[1].trim());
							Client newClient = new Client(tokens[1].trim(), packet.getAddress(), packet.getPort());
							gameServer.addPlayer(newClient);
							gameServer.send("CONNECTED", newClient);
						}
						
						// updating players who are ready
						else if(messageReceived.startsWith("READYNAKO+")){
							String tokens[] = messageReceived.split("\\+");
							gameServer.send("OKAY", tokens[1]);
							gameServer.makePlayerReady(tokens[1].trim());
						}

						else{
							System.out.println("Wrong message!");
						}
						break;
					
					case Constants.GAME_START:
						// action from player client
						if(messageReceived.startsWith("ACTION")){
							String tokens[] = messageReceived.split("\\+");
							gameServer.doAction(messageReceived);
						}
						break;
				}
			} catch(Exception e){
			}
			
			
		}
	}
}
