package chatserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import constants.Constants;
import game.Game;

public class ClientListener extends Thread {
	private Game game;
	private Client client;
	private BufferedReader in;
	private Broadcaster broadcaster;
	private ClientReceiver clientReceiver;
	//client listener constructor
	public ClientListener(Client c, ClientReceiver cr, Game game){
		this.game = game;
		this.client = c;
		this.broadcaster = cr.getBroadcaster();
		this.clientReceiver = cr;

		try{
			in = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//method to start thread execution
	@Override
	public void run(){
		String msg = null;

		try {
			while((msg = in.readLine()) != null){
				if(msg.equals(Constants.LEAVE_GAME)){
					break;
				}
				else{
					broadcaster.sendMessage(client.username, msg);
					game.receiveMessage(client.username, msg);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			game.receiveMessage("** ( "+ client.username + " ) HAS DISCONNECTED!");
			broadcaster.broadcast("** ( "+ client.username + " ) HAS DISCONNECTED!");
			clientReceiver.removeClient(client.username);
			game.removeGameClient(client.username);
		}
	}

}

