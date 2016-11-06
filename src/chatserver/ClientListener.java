package chatserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import controller.Game;

public class ClientListener extends Thread {
	private Game game;
	private Client client;
	private BufferedReader in;
	private Broadcaster broadcaster;
	private ClientReceiver clientReceiver;

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

	public void run(){
		String msg = null;
		boolean printFlag = true;

		try {
			while((msg = in.readLine()) != null){
				if(msg.equals("~!exit")){
					broadcaster.broadcast("** "+client.username+ " has DISCONNECTED! eme");
					clientReceiver.removeClient(client.username);
					printFlag = false;
				}
				else{
					broadcaster.sendMessage(client.username, msg);
					game.receiveMessage(client.username, msg);
				}
			}
			if(printFlag){
				broadcaster.broadcast("** "+client.username+ " has DISCONNECTED! omo");
			}
		}
		catch(NullPointerException e){
			System.out.println("** " + client.username + " has DISCONNECTED! imi");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}

