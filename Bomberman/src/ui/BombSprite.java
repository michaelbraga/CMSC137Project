package ui;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class BombSprite {
	private Animation bombSprite;
	private int duration = 4000; // 4 seconds;
	
	public BombSprite(float x, float y){
		try {
			bombSprite = new Animation(new SpriteSheet("res/sprite/bomb.png", 39, 39), 200);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bombSprite.draw(Bomberman.TILE_WIDTH*x + 4, Bomberman.TILE_HEIGHT*y);
		bombSprite.start();
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bombSprite.stop();
	}
}
