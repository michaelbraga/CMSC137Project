package chatclient;

import java.io.PrintWriter;

public class MessageSender {
	private PrintWriter out;
	//message sender class constructor
	public MessageSender(PrintWriter outToServer){
		this.out = outToServer;
	}
	//method used for sending message from client to the server for chat
	public void sendMessage(String message){
		try{
			this.out.println(message);
			this.out.flush();
		}
		catch(Exception e){
			System.out.println("Error: Cannot send message!");
			e.printStackTrace();
		}
	}
}
