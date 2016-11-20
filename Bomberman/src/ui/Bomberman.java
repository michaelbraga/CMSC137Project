package ui;

import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.tiled.TiledMap;

import constants.Constants;
import game.Game;

public class Bomberman extends BasicGame{
	private TextField bombField;
	private Game game;
	private int gameStatus = Constants.WAITING_FOR_PLAYERS;
	private TiledMap map;
	public static int TILE_WIDTH, TILE_HEIGHT;
	
	
	private float x=1f, y=1f;
	private Animation bombSprite;
	
	public Bomberman(Game game) {
		super("Bomberman");
		this.game=game;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		switch(gameStatus){
			case Constants.WAITING_FOR_PLAYERS:
				waitingForPlayers(gc, g);
				break;
			case Constants.WAITING_BUT_READY:
				waitingButReady(gc, g);
				break;
			case Constants.GAME_START:
				showGame(gc, g);
				break;
		}
	}

	private void showGame(GameContainer gc, Graphics g) {
		map.render(0, 0);
		g.fillRoundRect(TILE_WIDTH*x, TILE_HEIGHT*y, TILE_WIDTH, TILE_HEIGHT, 5);
		
			bombSprite.draw(TILE_WIDTH*(x+2) +4, TILE_HEIGHT*(y+3));
			bombSprite.draw(TILE_WIDTH*(1) +4, TILE_HEIGHT*(11));
	}

	private void waitingButReady(GameContainer gc, Graphics g) {
		g.setColor(new Color(4, 76, 41));
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());

		g.setColor(Color.yellow);
		g.drawString("Waiting for other players...", gc.getWidth()/3, gc.getHeight()/2);
	}

	private void waitingForPlayers(GameContainer gc, Graphics g) {
		g.setColor(new Color(4, 76, 41));
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());

		g.setColor(Color.yellow);
		String text = (game.getPlayer().isHost())? "Waiting for other players. Type 'play' to be start":"Waiting for other players. Type 'bomb' to be ready :)";
		g.drawString(text, gc.getWidth()/5, gc.getHeight()/3);

		g.setColor(Color.white);
		bombField.render(gc, g);
	}

	@Override
	public void init(GameContainer gc){
		
		try {
			Font font = new TrueTypeFont(new java.awt.Font(java.awt.Font.MONOSPACED,java.awt.Font.PLAIN , 22), false);
			bombField = new TextField(gc, font, gc.getWidth()/3+25, gc.getHeight()/2-50, 200, 30);
			bombField.setBorderColor(Color.darkGray);
			bombField.setBackgroundColor(Color.gray);
			
			map = new TiledMap("res/map1.tmx");
			TILE_HEIGHT = map.getTileHeight();
			TILE_WIDTH = map.getTileWidth();
			
			bombSprite = new Animation(new SpriteSheet("res/sprite/bomb.png", 39, 39), 200);
		} catch (Exception e) {
			e.printStackTrace();
			game.dialogInGame("Error in Slick2D!");
			System.exit(1);
		}
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		if(gameStatus == Constants.WAITING_FOR_PLAYERS && gc.getInput().isKeyPressed(Input.KEY_ENTER)){
			if(bombField.getText().trim().equals("bomb") && !game.getPlayer().isHost()){
				gameStatus = Constants.WAITING_BUT_READY;
				game.makeReady();
				bombField = null;
			}
			else if(bombField.getText().trim().equals("play") && game.getPlayer().isHost()){
				game.start();
				bombField.setText("");
			}
		}
	}

	public void startGame() {
		this.gameStatus = Constants.GAME_START;
	}

	
}