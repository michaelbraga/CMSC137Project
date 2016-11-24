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
	
	public Player(String username, boolean isHost){
		this.username = username;
		this.lives = 3;
		this.isHost = isHost;
	}
	
	public Player(String username){
		this.username = username;
		this.lives = 3;
		this.ready = false;
		this.canDeployBomb = true;
	}

	public Player(String username, int lives) {
		this.username = username;
		this.lives = 3;
		this.ready = false;
		this.canDeployBomb = true;
	}

	public String getUsername() {
		return username;
	}

	public int getLives() {
		return lives;
	}
	
	public boolean isHost() {
		return isHost;
	}

	public void makeReady() {
		this.ready = true;
	}

	public boolean isReady() {
		return this.ready;
	}

	public void setLives(int i) {
		this.lives = i;
	}
}
