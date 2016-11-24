package gameclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerListener extends Thread{
	private GameClient gameClient;
	private DatagramSocket clientSocket;
	
	public ServerListener(GameClient gameClient){
		this.gameClient = gameClient;
		this.clientSocket = gameClient.getClientSocket();
	} 
	
	@Override
	public void run(){
		while(true){
			byte[] buffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			
			try {
				clientSocket.receive(packet);
				String messageReceived = new String(packet.getData()).trim();
				analyzeMessage(messageReceived);
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
	}

	private void analyzeMessage(String messageReceived) {
		
		if(messageReceived.startsWith("STARTNA")){
			gameClient.startGame();
		}
		else if(messageReceived.startsWith("UPDATE")){
			gameClient.updateGameState(messageReceived);
		}
		else{
			System.out.println(messageReceived);
		}
	}
}
