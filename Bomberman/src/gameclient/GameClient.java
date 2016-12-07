package gameclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import constants.Constants;
import game.Game;
import gameserver.GameState;

public class GameClient{
	private DatagramSocket clientSocket;
	private boolean connected = false;
	private Game game;
	private ServerListener serverListener;
	private String username;
	private GameState gameState;
	
	//game client class constructor
	public GameClient(Game game, String username){
		this.game = game;
		this.username = username;
		try {
			clientSocket = new DatagramSocket();
			clientSocket.setSoTimeout(2000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clientSocket = null;
			serverListener = null;
		}
	}
	//method for thread execution of the game client class
	public void start(){
		serverListener = new ServerListener(this);
	}
	//method to connect client game socket to host game socket
	public boolean connect() {
		if(!connected && clientSocket != null){
			while(true){
				try {
					// ask for connection
					if(!connected) send("PACONNECT+" + username);
					// wait for response
					byte[] response = new byte[256];
					DatagramPacket packet = new DatagramPacket(response, response.length);
					clientSocket.receive(packet);
					String message = new String(packet.getData()).trim();
					
					if(message.equals("CONNECTED")){
						connected = true;
						return true;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	//method to send client actions to server
	private void send(String message) {
		// TODO Auto-generated method stub
		byte[] letter = new byte[256];
		letter = new String(message.toString()).getBytes();
		DatagramPacket packet;
		try {
			packet = new DatagramPacket(letter, letter.length, InetAddress.getByName(game.getServerIp()), Constants.UDP_PORT);
			clientSocket.send(packet);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//method to check if client is connected to the game
	public boolean isConnected() {
		return connected;
	}
	//mtehod to get client udp socket
	public DatagramSocket getClientSocket() {
		return this.clientSocket;
	}
	//method to close / end connection with the socket
	public void close() {
		this.clientSocket.close();
	}
	//method to require players to get ready before entering the game
	public void makeReady() {
		while(true){
			try {
				// ask for connection
				send("READYNAKO+" + username);
				// wait for response
				byte[] response = new byte[256];
				DatagramPacket packet = new DatagramPacket(response, response.length);
				clientSocket.receive(packet);
				String message = new String(packet.getData()).trim();
				
				if(message.equals("OKAY")){
					serverListener.start();
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}
	//method for displaying in game dialog
	public void m(String string) {
		game.dialogInGame(string);
	}
	//method to start the ui game class
	public void startGame() {
		game.start();
	}
	//method for sending client action to server
	public void sendGameAction(String action) {
		send(action);
	}
	//method to set client game state
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	//method to update client game state
	public void updateGameState(String update) {
		this.gameState.update(update);
	}
}
