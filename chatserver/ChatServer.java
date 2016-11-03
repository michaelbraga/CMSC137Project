package chatserver;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ChatServer {
	private int portNumber;
	private String username;

	private ServerSocket serverSocket = null;
	private ArrayList<Client> clientList = null;

	private ClientReceiver clientReceiver = null;
	private Broadcaster broadcaster = null;


	public ChatServer(int port, String username){
		this.portNumber = port;
		this.username = username;

		try{
			this.serverSocket = new ServerSocket(port);
			this.clientList = new ArrayList<>();
			System.out.println("*Chat server is now listening at port: " + port);
			System.out.println("'~!exit' to leave.\n");

		}
		catch(Exception e){
			System.out.println("*Cannot listen at port: " + port);
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void run(){
		broadcaster = new Broadcaster(clientList);
		clientReceiver = new ClientReceiver(serverSocket, clientList, broadcaster, username);
		clientReceiver.start();

		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
		String msg;
		while(true){
			try{
				msg = userInput.readLine();
				if(msg.equals("~!exit")){
					broadcaster.broadcast("**"+username+"[host] has DISCONNECTED!");
					break;
				}else{
					broadcaster.broadcast("("+username + "): "+msg);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		stop();
	}

	public void stop() {
		try{
			this.clientReceiver.stop();
			this.serverSocket.close();
			System.out.println("*Server socket at "+ portNumber +" is now close");
			System.exit(-1);
		}
		catch(Exception e){
			System.out.println("*Cannot close sockets!");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
