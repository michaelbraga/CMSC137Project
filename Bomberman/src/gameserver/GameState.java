package gameserver;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import player.Player;

public class GameState {
	TiledMap map;
	ArrayList<Client> players;
	
	public GameState(TiledMap map){
		this.map = map;
		this.players = new ArrayList<>();
	}
	
	public String toString(){
		String gameState = "";
		gameState += "PLAYERS";
		for(Client p: players){
			gameState += "+" + p.toString();
		}
		return gameState;
	}

	public void setPlayers(ArrayList<Client> players) {
		this.players = players;
	}

	public void update(String update) {
		System.out.println(update);
		String[] tokens = update.split("~");
		if(tokens[0].equals("UPDATE")){
			String[] playerInfo = tokens[1].split("\\+");
			// tokens[1] PLAYERs
			// for every player
				// username
				// position X
				// position Y
				// lives
			if(players.size() == 0){
				
				for(int i=1; i<playerInfo.length; i+=4){
					players.add(new Client(playerInfo[i], playerInfo[i+1], playerInfo[i+2], playerInfo[i+3]));
				}
			}
			else{
				Client p;
				for(int i=1; i<playerInfo.length; i+=4){
					p = getPlayer(playerInfo[i]);
					p.update(playerInfo[i+1], playerInfo[i+2], playerInfo[i+3]);
				}
			}
		}
	}
	
	private Client getPlayer(String username) {
		for(Client c: players){
			if(c.getUsername().equals(username))
				return c;
		}
		return null;
	}

	public TiledMap getMap() {
		return this.map;
	}

	public ArrayList<Client> getPlayers() {
		return this.players;
	}
	
	
}
