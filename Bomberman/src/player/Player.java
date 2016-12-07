package player;

import java.awt.Point;

public class Player {
	public final static boolean HOST = true;
	public final static boolean CLIENT = false;
	
	private String username;
	private int lives;
	private boolean ready;
	private boolean canDeployBomb;
	
	// NOT USED IN UDP 
	private boolean isHost;
	//player class constructor
	public Player(String username, boolean isHost){
		this.username = username;
		this.lives = 3;
		this.isHost = isHost;
	}
	//player class constructor
	public Player(String username){
		this.username = username;
		this.lives = 3;
		this.ready = false;
		this.canDeployBomb = true;
	}
	//player class constructor
	public Player(String username, int lives) {
		this.username = username;
		this.lives = 3;
		this.ready = false;
		this.canDeployBomb = true;
	}
	//method to get player username
	public String getUsername() {
		return username;
	}
	//method to get player remaining lives
	public int getLives() {
		return lives;
	}
	//method to retrieve if player is game hsot
	public boolean isHost() {
		return isHost;
	}
	//method to set player in ready state
	public void makeReady() {
		this.ready = true;
	}
	//method to retrieve if player is in ready state
	public boolean isReady() {
		return this.ready;
	}
	//method to set player remaining lives
	public void setLives(int i) {
		this.lives = i;
	}
}
