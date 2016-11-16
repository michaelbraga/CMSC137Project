package ui;

import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class Bomberman extends BasicGame{
	private TiledMap map;
	private Animation sprite, up, down, left, right;
	private float x= 1f, y=1f;

	public Bomberman() {
		super("Bomberman");
		
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		map.render(0, 0);
		sprite.draw((int)map.getTileWidth()*x, (int)map.getTileHeight()*y);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
//		// TODO Auto-generated method stub
		try {
			map = new TiledMap("res/mapa.tmx");
			
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpriteSheet u = new SpriteSheet("res/sprite/up.png", 30, 44);
		SpriteSheet d = new SpriteSheet("res/sprite/down.png", 30, 44);
		SpriteSheet l = new SpriteSheet("res/sprite/left.png", 30, 44);
		SpriteSheet r = new SpriteSheet("res/sprite/right.png", 30, 44);
		
		up = new Animation(u, 300);
		down = new Animation(d, 300);
		left = new Animation(l, 300);
		right = new Animation(r, 300);
		sprite = right;
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		Input input = arg0.getInput();
		long delta = (long) 1f; 
		if (input.isKeyDown(Input.KEY_UP))
		{
		    sprite = up;
		    sprite.update(delta);
		    // The lower the delta the slowest the sprite will animate.
		    y -= delta * 0.05f;
		}
		else if (input.isKeyDown(Input.KEY_DOWN))
		{
		    sprite = down;
		    sprite.update(delta);
		    y += delta * 0.05f;
		}
		else if (input.isKeyDown(Input.KEY_LEFT))
		{
		    sprite = left;
		    sprite.update(delta);
		    x -= delta * 0.05f;
		}
		else if (input.isKeyDown(Input.KEY_RIGHT))
		{
		    sprite = right;
		    sprite.update(delta);
		    x += delta * 0.05f;
		}

	}
	
}
