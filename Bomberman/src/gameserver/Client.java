package gameserver;

import java.awt.Point;
import java.net.InetAddress;

import player.Player;

public class Client {
	private InetAddress address;
	private int port;
	private String username;
	private Player player;
	private Point spawnPoint;
	private Point position;
	
	public Client(String username, InetAddress address, int port){
		this.username = username;
		this.address = address;
		this.port = port;
		this.player = new Player(username);
	}	
	
	@Override
	public String toString(){
		return this.username + " " + this.address.toString() + " " + this.port;
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return this.username;
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
}
