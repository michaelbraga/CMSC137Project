package chatserver;

import java.net.*;

public class Client {
	public Socket socket;
	public int port;
	public String username;

	private ClientListener listener;
	private ClientReceiver clientReceiver;

	public Client(Socket s, String un, ClientReceiver cr){
		this.socket = s;
		this.username = un;

		this.clientReceiver = cr;
		this.listener = new ClientListener(this, cr);
	}

	public void listen(){
		this.listener.start();
	}
}
