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
    
	private BufferedImage intro, playButton, WingspanImage;

    public IntroPanel(){
    	try {
    		
    		intro = ImageIO.read(IntroPanel.class.getResource("/Image/IntroImage.jpg"));
    		playButton = ImageIO.read(IntroPanel.class.getResource("/Image/PlayButton.png"));
    		WingspanImage = ImageIO.read(IntroPanel.class.getResource("/Image/LogoImage.png"));
    		
    		
    	}
    	catch(Exception e){
    		System.out.println("Exception error");
    		return;
    	}
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        
        Font font = new Font("Serif", Font.BOLD, 50);
        
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.green);
        g2d.setFont(font);
        BasicStroke stroke = new BasicStroke(10);
        g2d.setStroke(stroke);
        //dg2d.drawRect(0, 0, 1920, 1080);
        g2d.drawImage(intro, 0, 0, 1920, 1080,null);
        g2d.drawImage(playButton, getWidth()/2-200, getHeight()/2+300, 300,100, null);
        g2d.setColor(Color.BLACK);	
        g2d.drawString("PLAY!", getWidth()/2-110, getHeight()/2+60+300);
        g2d.drawImage(WingspanImage, getWidth()/2-200, 400, null);
        //g2d.fillRect(1540, 0, 400, 400);
        /*if (!GameState.gameStarted)
        {
            
        }*/
    }
}
