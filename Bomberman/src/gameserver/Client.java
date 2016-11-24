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
	
	public Client(String username, InetAddress address, int port){
		this.address = address;
		this.port = port;
		this.player = new Player(username);
	}	
	
	public Client(String username, String positionX, String positionY, String lives) {
		this.player = new Player(username, Integer.parseInt(lives));
		this.assignSpawnPosition(new Point((int)Float.parseFloat(positionX), (int)Float.parseFloat(positionY)));
	}

	@Override
	public String toString(){
		return String.join("+", this.getUsername(), position.getX()+"+"+position.getY(), player.getLives()+"");
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return this.player.getUsername();
	}

	public void makeReady() {
		this.player.makeReady();
	}

	public boolean isReady() {
		return this.player.isReady();
	}

	public void assignSpawnPosition(Point point) {
		this.spawnPoint = point;
		this.position = (Point) point.clone();
	}

	public float getPosX() {
		return (float) this.position.getX();
	}
	public float getPosY() {
		return (float) this.position.getY();
	}

	public void moveUp() {
		this.position.translate(0, -3);
	}
	
	public void moveDown() {
		this.position.translate(0, +3);
	}
	
	public void moveLeft() {
		this.position.translate(-3, 0);
	}
	
	public void moveRight() {
		this.position.translate(+3, 0);
	}

	public void update(String posX, String posY, String lives) {
		this.position.setLocation(Double.parseDouble(posX), Double.parseDouble(posY));
		this.player.setLives(Integer.parseInt(lives));
	}
}
