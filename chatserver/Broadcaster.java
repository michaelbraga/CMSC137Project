package chatserver;

import java.net.*;
import java.io.*;
import java.util.*;

public class Broadcaster {
	private ArrayList<Client> clientList;
	private ArrayList<PrintWriter> portal;

	public Broadcaster(ArrayList<Client> cl){
		this.clientList = cl;
		this.portal = new ArrayList<>();
	}

	public synchronized void broadcast(String message){
		if(!message.isEmpty()){
			for (int i=0; i<portal.size(); i+=1) {
				portal.get(i).println(message);
				portal.get(i).flush();
			}
		}
	}

	public synchronized void sendMessage(String sender, String message){
		if(!message.isEmpty()){
			for (int i=0; i<portal.size(); i+=1) {
				if(!clientList.get(i).username.equals(sender)){
					portal.get(i).println("("+sender+"): "+ message);
					portal.get(i).flush();
				}
			}
		}
	}

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
