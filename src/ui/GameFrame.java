package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Game;

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
		// TODO
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
	}

	public ChatPanel getChatPanel() {
		return chatPanel;
	}

}
