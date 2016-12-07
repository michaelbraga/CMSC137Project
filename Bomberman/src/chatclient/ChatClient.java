package chatclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import game.Game;

public class ChatClient{
	//Chat Client variables
	private Game game;
	private String ipAddress;
	private int portNumber;
	private String username;

	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private MessageReceiver mr = null;
	private MessageSender ms = null;

	//chat client class constructor	
	public ChatClient(String ip, int port, String name, Game game){
		this.game = game;
		this.game.setServerIp(ip);
		this.ipAddress = ip;
		this.portNumber = port;
		this.username = name;
	}
	//method for connecting chat client ip:socket combination to the host ip:socket
	public boolean connect(){
		try{
			this.socket = new Socket(ipAddress, portNumber);
			DataOutputStream toServer = new DataOutputStream(this.socket.getOutputStream());
			DataInputStream fromServer = new DataInputStream(this.socket.getInputStream());

			// check if full
			boolean full = fromServer.readBoolean();
			if(full){
				this.socket.close();
				this.socket = null;
				game.dialogInMenu("Cannot join game! Server is full.");
				return false;
			}

			// send username to server to check for duplicates
			toServer.writeUTF(this.username);
			toServer.flush();
			// wait for server response
			boolean requestAccepted = fromServer.readBoolean();
			// if server accepted the request return true, else return false
			if(!requestAccepted){
				this.socket.close();
				this.socket = null;
				game.dialogInMenu("Cannot join game! Username is already in use.");
			}
			return requestAccepted;
		}
		catch(Exception e){
			game.dialogInMenu("Cannot connect to server!");
			this.socket = null;
			return false;
		}
	}
	//method for the thread to run the chat client
	public void start(){
		if(this.socket != null){
			try{
				// Stream readers and writers for send and receiving strings
				this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	           	this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
				// thread for receiving message
				this.mr = new MessageReceiver(this.in, this, game);
				this.mr.start();
				// object for sending message
				this.ms = new MessageSender(this.out);
				System.out.println("*Connected to chat server at port " + portNumber);
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Cannot get streams from sockets input/ouput!");
			}
		}
		else{
			System.out.println("Error: Connection to server has not been established!");
		}
	}
	//method for releasing the chat client connection
	@SuppressWarnings("deprecation")
	public void close(int stat){
		try{
			if(stat == 1) this.mr.stop();
			if(this.in != null) this.in.close();
			if(this.out != null) this.out.close();
			if(this.socket != null) this.socket.close();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String text) {
		ms.sendMessage(text);
	}
}
