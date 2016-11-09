package player;


public class Player {
	public final static boolean HOST = true;
	public final static boolean CLIENT = false;
	
	private String username;
	private int lives;
	private boolean ready;
	private boolean isHost;
	
	public Player(String username, boolean isHost){
		this.username = username;
		this.lives = 3;
		ready = false;
		this.isHost = isHost;
	}

	public String getUsername() {
		return username;
	}

	public int getLives() {
		return lives;
	}

	public boolean isReady() {
		return ready;
	}
	
	public boolean isHost() {
		return isHost;
	}
}
