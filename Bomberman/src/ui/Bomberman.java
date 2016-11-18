package ui;

import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Bomberman extends BasicGame{
	private TiledMap map;
	private Animation sprite, up, down, left, right;
	private float x= 1f, y=1f;
	private boolean[][] blocked;
	
	public Bomberman() {
		super("Bomberman");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(new Color(4, 76, 41));
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());

		g.setColor(Color.yellow);
		g.drawString("Waiting for other players...", gc.getWidth()/3, gc.getHeight()/2);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
	
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
	}
	
}