package gameserver;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import player.Player;

public class GameState {
	TiledMap map;
	ArrayList<Client> players;
	private ArrayList<Point> bombLocations;
	//game state class constructor
	public GameState(TiledMap map){
		this.map = map;
		this.players = new ArrayList<>();
		this.bombLocations = new ArrayList<>();
	}
	//game state method to parse game contents to string
	public String toString(){
		String gameState = "";
		gameState += "PLAYERS";
		for(Client p: players){
			gameState += "+" + p.toString();
		}
		gameState += "#BOMBS+" + this.getBombLocations().size();
		for(Point p: this.getBombLocations()){
			gameState += "+" + p.getX() + "+" + p.getY();
		}
		return gameState;
	}
	//method to set game state players 
	public void setPlayers(ArrayList<Client> players) {
		this.players = players;
	}
	//method to update game state via the string update to be parsed
	public void update(String update) {
		System.out.println(update);
		String[] tokens = update.split("~");
		if(tokens[0].equals("UPDATE")){
			String[] tok = tokens[1].split("#");
			String[] playerInfo = tok[0].split("\\+");
			String[] bombInfo = tok[1].split("\\+");
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
			
			for(int i=0, b=0; b<Integer.parseInt(bombInfo[1]); i+=2, b+=1){
				Point p = new Point((int)Double.parseDouble(bombInfo[i+2]), (int)Double.parseDouble(bombInfo[i+2+1]));
				this.addBombLocation(p);
				System.out.println(p.toString());
			}
		}
	}
	//method to get game state player
	private Client getPlayer(String username) {
		for(Client c: players){
			if(c.getUsername().equals(username))
				return c;
		}
		return null;
	}
	//method to get the map contents
	public TiledMap getMap() {
		return this.map;
	}
	//method to get list of players
	public ArrayList<Client> getPlayers() {
		return this.players;
	}
	//method to get list of bomb locations
	public ArrayList<Point> getBombLocations() {
		return this.bombLocations;
	}
	//method to set a bomb in the map
	public boolean addBombLocation(Point point) {
		for(Point p: this.bombLocations){
			if(p.getX() == point.getX() && p.getY() == point.getY()){
				return false;
			}
		}
		this.bombLocations.add(point);
		return true;
	}
	
	
	
}
