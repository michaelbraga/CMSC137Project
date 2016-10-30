package chatclient;

import java.net.*;
import java.io.*;

public class MessageReceiver extends Thread {
	private BufferedReader in;
	private ChatClient chatClient;

	public MessageReceiver(BufferedReader inFromServer, ChatClient chatClient){
		this.in = inFromServer;
		this.chatClient = chatClient;
	}

	public void run(){
		String messageReceived = null;
		try{
			while((messageReceived = in.readLine().toString()) != null){
				System.out.println(messageReceived);
			}
		}
		catch(NullPointerException e){
			System.out.println("Server disconnected!");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		chatClient.close(0);
	}
}
