package gameserver;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class GameState {
	TiledMap map;
	ArrayList<Client> players;
	private boolean ready = false;
	
	public GameState(String mapFile){
		try {
			map = new TiledMap(mapFile);
			players = new ArrayList<>();
			ready = true;
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setPlayers(ArrayList<Client> players) {
		this.players = players;
	}

	public TiledMap getMap() {
		return map;
	}

	public boolean ready() {
		return ready ;
	}
}
