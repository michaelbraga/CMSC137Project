package chatserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.ArrayList;

import controller.Game;

public class ChatServer {
	private Game game;
	private int portNumber;
	private String username;
	private boolean okay = false;

	private ServerSocket serverSocket = null;
	private ArrayList<Client> clientList = null;

	private ClientReceiver clientReceiver = null;
	private Broadcaster broadcaster = null;


	public ChatServer(int port, String username, Game game){
		this.portNumber = port;
		this.username = username;
		this.game = game;
	}
	
	public boolean host(){
		try{
			this.serverSocket = new ServerSocket(portNumber);
			this.clientList = new ArrayList<>();
			System.out.println("*Chat server is now listening at port: " + portNumber);
			return true;
		}
		catch(Exception e){
			System.out.println("*Cannot listen at port: " + portNumber);
			e.printStackTrace();
			this.serverSocket = null;
			return false;
		}
	}

	public void run(){
		if(serverSocket != null){
			broadcaster = new Broadcaster(clientList);
			clientReceiver = new ClientReceiver(serverSocket, clientList, broadcaster, username, game);
			clientReceiver.start();
			
			okay = true;
		}
	}

	public void stop() {
		try{
			this.clientReceiver.stop();
			this.serverSocket.close();
			System.out.println("*Server socket at "+ portNumber +" is now close");
		}
		catch(Exception e){
			System.out.println("*Cannot close sockets!");
			e.printStackTrace();
		}
	}

	public void sendMessage(String text) {
		broadcaster.sendMessage(game.getPlayerName(), text);
	}
}
