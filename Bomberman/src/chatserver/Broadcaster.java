package chatserver;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Broadcaster {
	private ArrayList<Client> clientList;
	private ArrayList<PrintWriter> portal;
	//chat message broadcaster class constructor
	public Broadcaster(ArrayList<Client> cl){
		this.clientList = cl;
		this.portal = new ArrayList<>();
	}
	//broadcast method to send received message from a client to all clients except the sender
	public synchronized void broadcast(String message){
		if(!message.isEmpty()){
			for (int i=0; i<portal.size(); i+=1) {
				portal.get(i).println(message);
				portal.get(i).flush();
			}
		}
	}
	//method for sending the string message
	public synchronized void sendMessage(String sender, String message){
		if(!message.isEmpty()){
			for (int i=0; i<portal.size(); i+=1) {
				if(!clientList.get(i).username.equals(sender)){
					portal.get(i).println("( "+sender+" ): "+ message);
					portal.get(i).flush();
				}
			}
		}
	}
	//method for updating portals of the chat system
	public void updatePortals(){
		portal.clear();
		try{
			for (int i=0; i<clientList.size(); i+=1) {
				Socket socket = clientList.get(i).socket;
				portal.add(new PrintWriter(new OutputStreamWriter(socket.getOutputStream())));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}