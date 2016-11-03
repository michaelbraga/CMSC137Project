package chatserver;

import java.net.*;
import java.io.*;
import java.util.*;

public class ClientListener extends Thread {
	private Client client;
	private BufferedReader in;
	private Broadcaster broadcaster;
	private ClientReceiver clientReceiver;

	public ClientListener(Client c, ClientReceiver cr){
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
					System.out.println("** "+client.username+ " has DISCONNECTED!");
					broadcaster.broadcast("** "+client.username+ " has DISCONNECTED!");
					clientReceiver.removeClient(client.username);
					printFlag = false;
				}
				else{
					System.out.println("("+client.username+ "): " + msg);
					broadcaster.sendMessage(client.username, msg);
				}
			}
			if(printFlag){
				System.out.println("** "+client.username+ " has DISCONNECTED!");
				broadcaster.broadcast("** "+client.username+ " has DISCONNECTED!");
			}
		}
		catch(NullPointerException e){
			System.out.println(client.username + " has DISCONNECTED!");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
