package gameserver;

import java.awt.Point;
import java.net.InetAddress;

import player.Player;

public class Client {
	private InetAddress address;
	private int port;
	private Player player;
	private Point spawnPoint;
	private Point position;
	//game server client class constructor
	public Client(String username, InetAddress address, int port){
		this.address = address;
		this.port = port;
		this.player = new Player(username);
	}	
	//game server client class constructor
	public Client(String username, String positionX, String positionY, String lives) {
		this.player = new Player(username, Integer.parseInt(lives));
		this.assignSpawnPosition(new Point((int)Float.parseFloat(positionX), (int)Float.parseFloat(positionY)));
	}
	//method for putting the game update to string
	@Override
	public String toString(){
		return String.join("+", this.getUsername(), position.getX()+"+"+position.getY(), player.getLives()+"");
	}
	//method to get client ip address
	public InetAddress getAddress() {
		return address;
	}
	//method to get client port
	public int getPort() {
		return port;
	}
	//method to get client player username
	public String getUsername() {
		return this.player.getUsername();
	}
	//method to set client to ready state
	public void makeReady() {
		this.player.makeReady();
	}
	//method to retrieve if client is in ready state
	public boolean isReady() {
		return this.player.isReady();
	}
	//method to set player in a spawn area
	public void assignSpawnPosition(Point point) {
		this.spawnPoint = point;
		this.position = (Point) point.clone();
	}
	//method to get client x axis position on the map
	public float getPosX() {
		return (float) this.position.getX();
	}
	//method to get client y axis position on the map
	public float getPosY() {
		return (float) this.position.getY();
	}
	//method to move player up by 3 units
	public void moveUp() {
		this.position.translate(0, -3);
	}
	//method to move player down by 3 units
	public void moveDown() {
		this.position.translate(0, +3);
	}
	//method to move player left by 3 units
	public void moveLeft() {
		this.position.translate(-3, 0);
	}
	//method to move player right by 3 units
	public void moveRight() {
		this.position.translate(+3, 0);
	}
	//method to update game units of player location and player remaining lives
	public void update(String posX, String posY, String lives) {
		this.position.setLocation(Double.parseDouble(posX), Double.parseDouble(posY));
		this.player.setLives(Integer.parseInt(lives));
	}
}
