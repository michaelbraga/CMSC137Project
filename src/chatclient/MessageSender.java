package chatclient;

import java.io.PrintWriter;

public class MessageSender {
	private PrintWriter out;

	public MessageSender(PrintWriter outToServer){
		this.out = outToServer;
	}

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
