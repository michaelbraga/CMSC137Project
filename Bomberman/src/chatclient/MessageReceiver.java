package chatclient;

import java.io.BufferedReader;

import constants.Constants;
import game.Game;

public class MessageReceiver extends Thread {
	private Game game;
	private BufferedReader in;
	private ChatClient chatClient;
	//message receiver constructor	
	public MessageReceiver(BufferedReader inFromServer, ChatClient chatClient, Game game){
		this.game = game;
		this.in = inFromServer;
		this.chatClient = chatClient;
	}
	//method for message receiver thread execution
	@Override
	public void run(){
		String messageReceived = null;
		try{
			while((messageReceived = in.readLine()) != null){
				if(messageReceived.equals(Constants.LEAVE_GAME))
					break;
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
		game.disconnection("Server disconnected!");
	}
}
