package ui;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class TutorialGame extends BasicGame {
	
	public TutorialGame(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawString("Hello World!",200,200);
		
		g.setColor(new Color(0x0affbb));
		
		for(int i = 0;i<(arg0.getWidth()/10);i++){
			g.drawRect(i*10,0,10,10);
		}
		
		for(int i = 0;i<(arg0.getHeight()/10);i++){
			g.drawRect(0,i*10,10,10);
		}
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub

	}


}
