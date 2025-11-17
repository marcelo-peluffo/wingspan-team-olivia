import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class IntroPanel extends JPanel{
    
	private BufferedImage intro, playButton;

    public IntroPanel(){
    	try {
    		
    		intro = ImageIO.read(IntroPanel.class.getResource("/Image/IntroImage.jpg"));
    		playButton = ImageIO.read(IntroPanel.class.getResource("/Image/PlayButton.png"));
    		
    		
    	}
    	catch(Exception e){
    		System.out.println("Exception error");
    		return;
    	}
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        
        Font font = new Font("Serif", Font.PLAIN, 50);
        
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.green);
        g2d.setFont(font);
        BasicStroke stroke = new BasicStroke(10);
        g2d.setStroke(stroke);
        g2d.drawRect(0, 0, 1920, 1080);
        g2d.drawImage(intro, 5, 5, 1910, 1070,null);
        g2d.drawImage(playButton, getWidth()/2-200, getHeight()/2, 300,100, null);
        g2d.setColor(Color.BLACK);	
        g2d.drawString("PLAY!", getWidth()/2-110, getHeight()/2+60);
        /*if (!GameState.gameStarted)
        {
            
        }*/
    }
}
