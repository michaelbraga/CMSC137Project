package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerLives extends JPanel {
		private String img;
		private int livescount;

	public PlayerLives (String img){
		this.livescount=3;	
		this.img=img;

		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridLayout(1,2 ));				
		
		try {
 			BufferedImage pic = ImageIO.read(new File(img));
			ImageIcon imgicon = new ImageIcon(pic.getScaledInstance(40, 40,java.awt.Image.SCALE_SMOOTH));
			JLabel picLabel = new JLabel(imgicon);
		
			JLabel count = new JLabel("3");
			count.setFont(new Font("Serif", Font.PLAIN, 25));

			this.add(picLabel);	
			this.add(count);
	
		}catch( IOException io ) {}	
	}
}
