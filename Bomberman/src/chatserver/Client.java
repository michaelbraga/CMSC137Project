package chatserver;

import java.net.Socket;

import game.Game;

public class Client {
	public Socket socket;
	public int port;
	public String username;

	private ClientListener listener;
	//client class constructor
	public Client(Socket s, String un, ClientReceiver cr, Game game){
		this.socket = s;
		this.username = un;
		this.listener = new ClientListener(this, cr, game);
	}
	//method to start client listener class thread for chat system
	public void listen(){
		this.listener.start();
	}
}