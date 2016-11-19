package gameclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import constants.Constants;
import game.Game;

public class GameClient{
	private DatagramSocket clientSocket;
	private boolean connected = false;
	private Game game;
	private ServerListener serverListener;
	private String username;
	
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
	
	public void start(){
		serverListener = new ServerListener(this);
	}
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

	public boolean isConnected() {
		return connected;
	}

	public DatagramSocket getClientSocket() {
		return this.clientSocket;
	}

	public void close() {
		this.clientSocket.close();
	}

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

	public void m(String string) {
		game.dialogInGame(string);
	}

	public void startGame() {
		game.start();
	}
}
