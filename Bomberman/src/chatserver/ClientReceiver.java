package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import game.Game;

public class ClientReceiver extends Thread {
	private Game game;
	private String servername;
	private ServerSocket serverSocket = null;
	private ArrayList<Client> clientList = null;
	private Broadcaster broadcaster;

	private boolean stillServing = true;

	//client receiver at server constructor
	public ClientReceiver(ServerSocket ss, ArrayList<Client> cl, Broadcaster b, String sn, Game game){
		this.game = game;
		serverSocket = ss;
		clientList = cl;
		broadcaster = b;
		servername = sn;
	}
	//method for starting thread execution
	@Override
	public void run(){
		while(stillServing){
			try{
				Socket potentialClient = serverSocket.accept();
				DataInputStream in = new DataInputStream(potentialClient.getInputStream());
				DataOutputStream out = new DataOutputStream(potentialClient.getOutputStream());
				boolean full;
				// if server is full
				if( full = clientList.size() >= 5 && stillServing){
					out.writeBoolean(full);
				}
				// if server is not full
				else{
					out.writeBoolean(full);
					// wait for username
					String username = in.readUTF().toString().trim();
					// check in list if already used
					boolean status = checkIfAvailable(username);
					// send result
					out.writeBoolean(status);
					if(status){
						broadcaster.broadcast("** ( "+username+" ) HAS JOINED THE GAME.");
						game.receiveMessage("** ( "+username+" ) HAS JOINED THE GAME.");
						Client newClient = new Client(potentialClient, username, this, game);
						clientList.add(newClient);
						broadcaster.updatePortals();
						newClient.listen();
					}
				}

			}
			catch(Exception e){
				System.out.println("Failed accepting a client!");
				e.printStackTrace();
			}
		}
	}
	//method to get the chatserver's broadcaster class object
	public Broadcaster getBroadcaster(){
		return this.broadcaster;
	}
	//method to check if a certain player username still exists on the game/server
	public boolean checkIfAvailable(String username){
		if(servername.equals(username)) return false;

		for(int i=0; i<clientList.size(); i+=1){
			if(clientList.get(i).username.equals(username)){
				return false;
			}
		}
		return true;
	}
	//method for removing a client on the chat system
	public void removeClient(String username){

		for(int i=0; i<clientList.size(); i+=1){
			if(clientList.get(i).username.equals(username)){
				clientList.remove(i);
				broadcaster.updatePortals();
			}
		}
	}

	public void stopServing() {
		this.stillServing = false;
	}
}
