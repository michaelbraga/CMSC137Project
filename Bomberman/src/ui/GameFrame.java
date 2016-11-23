package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;

import game.Game;

public class GameFrame extends JFrame {
	private final static int WIDTH = 1030; 
	private final static int HEIGHT = 615; 
	
	private Game game;
	private Container contentPane;
	/*
	 * Up Panel -> contains the remaining time box, and the player lives
	 * Down Panel -> contains the chat panel and the game panel
	 * */
	private JPanel upPanel;
	private JPanel downPanel;
	/*
	 * Up Panel Components
	 * */
	private JPanel timerPanel;	
	private JPanel livesPanel;
	private PlayerLives[] playerLivesPanel = new PlayerLives[6];
	/*
	 * Down Panel Components
	 * */
	private ChatPanel chatPanel;
	private CanvasGameContainer gamePanel;
	
	/* Bomberman game */
	private Bomberman bombermanGame;

	public GameFrame(Game game)  {
		super("BOMBERMAN - " + game.getPlayerName());
		this.game = game;
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(WIDTH, HEIGHT));
		setResizable(false);
		initComponents();
		this.setLocationRelativeTo(null);
	}
	
	private void initComponents() {
		contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		upPanel = new JPanel();
		upPanel.setMaximumSize(new Dimension(WIDTH, (int)(1.0/6.0 * HEIGHT)));
		downPanel = new JPanel();
		downPanel.setMaximumSize(new Dimension(WIDTH, (int)(5.0/6.0 * HEIGHT)));
		contentPane.add(upPanel);
		contentPane.add(downPanel);
		initUpPanel();
		initDownPanel();
		
		// for editing purposes
		downPanel.setBackground(Color.blue);
	}
	
	public void initUpPanel(){
		upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.Y_AXIS));
		
		//timer panel 
		timerPanel= new JPanel();
		timerPanel.setMaximumSize(new Dimension((WIDTH), (int)(0.3/6.0 * HEIGHT)));
		JLabel timeLabel = new JLabel("REMAINING TIME: [             ]");
		timeLabel.setFont(new Font("Serif", Font.BOLD, 15));
		
		timerPanel.add(timeLabel);
		timerPanel.setBackground(Color.GRAY);
		
		//player status panel
		livesPanel= new JPanel();
		livesPanel.setMaximumSize(new Dimension((WIDTH), (int)(0.6/6.0 * HEIGHT)));
		livesPanel.setLayout(new GridLayout(1, 5));
		livesPanel.setBackground(Color.black);	
		for(int i=1;i<=5;i++){
			playerLivesPanel[i] = new PlayerLives(i);	
			livesPanel.add(playerLivesPanel[i]);	
		}
		playerLivesPanel[1].activate();
		
		upPanel.add(timerPanel);
		upPanel.add(livesPanel);
	}
	
	public void initDownPanel(){
		downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
		chatPanel = new ChatPanel(game, new Dimension((int)(3.0/10.0 * WIDTH), (int)(5.0/6.0 * HEIGHT)));
		bombermanGame = null;
		bombermanGame = new Bomberman(game);
		try{
			gamePanel = new CanvasGameContainer(bombermanGame);
			gamePanel.setMaximumSize(new Dimension((int)(7.0/10.0 * WIDTH), (int)(5.0/6.0 * HEIGHT)));
		}catch(SlickException e){
			e.printStackTrace();
		}
		
		downPanel.add(chatPanel);
		downPanel.add(gamePanel);
		
		try {
			  gamePanel.getContainer().setAlwaysRender(true);
			  gamePanel.getContainer().setTargetFrameRate(60);
	          gamePanel.start();
	     } catch (SlickException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	     }
		
		// for editing purposes
		chatPanel.setBackground(Color.LIGHT_GRAY);
	}
	
	
	@Override
	public void dispose(){
		super.dispose();
		gamePanel.dispose();
		gamePanel = null;
	}

	public ChatPanel getChatPanel() {
		return chatPanel;
	}

	public void startGame() {
		this.bombermanGame.startGame();
	}

}
