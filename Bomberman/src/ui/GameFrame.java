package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.imageio.ImageIO; 
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Canvas;

import game.Game;

//SLICK MATERIALS
import ui.TutorialGame;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameFrame extends JFrame {
	private final static int WIDTH = 1000; 
	private final static int HEIGHT = 600; 
	
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
	private PlayerLives[] playerLivesPanel = new PlayerLives[5];
	/*
	 * Down Panel Components
	 * */
	private ChatPanel chatPanel;
	private JPanel gamePanel;

	public GameFrame(Game game) {
		this.game = game;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(WIDTH, HEIGHT));
		setResizable(false);
		initComponents();
	}
	
	private void initComponents(){
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
		upPanel.setBackground(Color.red);
		downPanel.setBackground(Color.blue);
	}
	
	public void initUpPanel(){
		upPanel.setLayout(new BoxLayout(upPanel, BoxLayout.Y_AXIS));
		
		//timer panel 
		timerPanel= new JPanel();
		timerPanel.setMaximumSize(new Dimension((int)(WIDTH), (int)(0.3/6.0 * HEIGHT)));
		JLabel timeLabel = new JLabel("REMAINING TIME: [             ]");
		timeLabel.setFont(new Font("Serif", Font.BOLD, 15));
		
		timerPanel.add(timeLabel);
		timerPanel.setBackground(Color.GRAY);
		
		//player status panel
		livesPanel= new JPanel();
		livesPanel.setMaximumSize(new Dimension((int)(WIDTH), (int)(0.6/6.0 * HEIGHT)));
		livesPanel.setLayout(new GridLayout(1, 5));
		livesPanel.setBackground(Color.black);	
		for(int i=0;i<5;i++){
			playerLivesPanel[i] = new PlayerLives("a.png");	
			livesPanel.add(playerLivesPanel[i]);	
		}

		upPanel.add(timerPanel);
		upPanel.add(livesPanel);
	}
	
	public void initDownPanel(){
		downPanel.setLayout(new BoxLayout(downPanel, BoxLayout.X_AXIS));
		chatPanel = new ChatPanel(game, new Dimension((int)(3.0/10.0 * WIDTH), (int)(5.0/6.0 * HEIGHT)));
		gamePanel = new JPanel();
		gamePanel.setMaximumSize(new Dimension((int)(7.0/10.0 * WIDTH), (int)(5.0/6.0 * HEIGHT)));
		downPanel.add(chatPanel);
		downPanel.add(gamePanel);
		initChatPanel();
		initGamePanel();
		
		// for editing purposes
		chatPanel.setBackground(Color.PINK);
		gamePanel.setBackground(Color.cyan);
	}
	
	public void initChatPanel(){
		// TODO
	}
	
	public void initGamePanel(){
		// TODO
		TutorialGame game = new TutorialGame("Bomberman");
		try {
	          CanvasGameContainer container = new CanvasGameContainer(game);
	          container.setBounds(20,20,400,400);
	          container.getContainer().setAlwaysRender(true);
	          gamePanel.add(container);
	          container.start();
	     } catch (SlickException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	     }
	}

	public ChatPanel getChatPanel() {
		return chatPanel;
	}

}
