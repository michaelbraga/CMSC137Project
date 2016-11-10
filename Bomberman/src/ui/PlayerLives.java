package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.imageio.ImageIO; 
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

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
