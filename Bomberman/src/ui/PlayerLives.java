package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerLives extends JPanel {
		private JLabel picLabel;
		
		private JPanel rightPanel;
		private JLabel countLabel;
		private JLabel playername;
		
		private int blocked;
	//player lives class constructor
	public PlayerLives(int playernum){
		this.blocked = 1;

		this.setBackground(Color.gray);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridLayout(1, 3));

		String imgfilename = "res/img/" + playernum + ".png";

		// add player image
		try {
			BufferedImage pic = ImageIO.read(new File(imgfilename));
			ImageIcon imgicon = new ImageIcon(pic.getScaledInstance(40, 40,
					java.awt.Image.SCALE_SMOOTH));
			picLabel = new JLabel(imgicon);
			this.add(picLabel);
		} catch (IOException io) {
			io.printStackTrace();
		}

		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBackground(Color.GRAY);
		
		// add the player lives count
		countLabel = new JLabel("0");
		countLabel.setFont(new Font("Serif", Font.PLAIN, 28));
		rightPanel.add(countLabel,BorderLayout.CENTER);
		
		//adds playername
		playername = new JLabel("PLAYER"+playernum);
		playername.setFont(new Font("Serif", Font.PLAIN, 13));
		rightPanel.add(playername,BorderLayout.SOUTH);
		
		this.add(rightPanel);
	}
	//method for initializing player lives ui and content
	public void activate(){
		this.countLabel.setText("3");
		this.setBackground(Color.white);
		rightPanel.setBackground(Color.white);
	}
	//method for updating player lives ui
	public void updateLives(int newCount){
		this.countLabel.setText(""+newCount);
		if(newCount==0){
			this.setBackground(Color.gray);
			this.blocked=1;
		}
	}
	//method to assign a cell into a player username
	public void setPlayerName(String name){
		this.playername.setText(name);
	}
}
