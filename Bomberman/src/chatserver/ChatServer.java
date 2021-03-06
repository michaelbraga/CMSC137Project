package chatserver;

import java.net.ServerSocket;
import java.util.ArrayList;

import game.Game;

public class ChatServer {
	private Game game;
	private int portNumber;
	private String username;

	private ServerSocket serverSocket = null;
	private ArrayList<Client> clientList = null;

	private ClientReceiver clientReceiver = null;
	private Broadcaster broadcaster = null;

	//chat server class constructor
	public ChatServer(int port, String username, Game game){
		this.portNumber = port;
		this.username = username;
		this.game = game;
	}
	//method to start chat server hosting of chat clients
	public boolean host(){
		try{
			this.serverSocket = new ServerSocket(portNumber);
			this.clientList = new ArrayList<>();
			System.out.println("*Chat server is now listening at port: " + portNumber);
			return true;
		}
		catch(Exception e){
			game.dialogInMenu("*Cannot listen at port: " + portNumber);
			e.printStackTrace();
			this.serverSocket = null;
			
			return false;
		}
	}
	//method to start the thread for client receivers to constantly accept for incoming broadcast messages
	public void start(){
		if(serverSocket != null){
			broadcaster = new Broadcaster(clientList);
			clientReceiver = new ClientReceiver(serverSocket, clientList, broadcaster, username, game);
			clientReceiver.start();
		}
	}
	//method for thread termination
	@SuppressWarnings("deprecation")
	public void stop() {
		try{
			this.clientReceiver.stop();
			this.serverSocket.close();
			System.out.println("*Chat server socket at "+ portNumber +" is now close");
		}
		catch(Exception e){
			System.out.println("*Cannot close sockets!");
			e.printStackTrace();
		}
	}
	//method using the broadcast class to send message to a player
	public void sendMessage(String text) {
		broadcaster.sendMessage(game.getPlayerName(), text);
	}
	//method using the broadcast class to broadcast message
	public void broadcast(String message) {
		broadcaster.broadcast(message);
	}
	//method to stop serving chat system to client receiver class
	public void stopAccepting() {
		clientReceiver.stopServing();
	}
}
