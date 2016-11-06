package chatclient;

import java.io.BufferedReader;

import controller.Game;

public class MessageReceiver extends Thread {
	private Game game;
	private BufferedReader in;
	private ChatClient chatClient;

	public MessageReceiver(BufferedReader inFromServer, ChatClient chatClient, Game game){
		this.game = game;
		this.in = inFromServer;
		this.chatClient = chatClient;
	}

	public void run(){
		String messageReceived = null;
		try{
			while((messageReceived = in.readLine().toString()) != null){
				game.receiveMessage(messageReceived);
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
