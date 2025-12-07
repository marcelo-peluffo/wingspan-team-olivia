package wingspan.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
//import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class IntroPanel extends JPanel implements MouseListener{
    
	private BufferedImage intro, playButton, WingspanImage;

    public IntroPanel(){
    	try {
    		
    		intro = ImageIO.read(getClass().getResourceAsStream("/Images/IntroImage.jpg"));
    		playButton = ImageIO.read(getClass().getResourceAsStream("/Images/PlayButton.png"));
    		WingspanImage = ImageIO.read(getClass().getResourceAsStream("/Images/LogoImage.png"));
    		
    		
    	}
    	catch(Exception e){
    		System.out.println("Exception error");
    		return;
    	}
    	
    	addMouseListener(this);
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
        g2d.drawImage(intro, 0, 0, 1920, 1080,null);
    
        g2d.drawImage(playButton, getWidth()/2-200, getHeight()/2+300, 300,100, null); // button to be clicked
    
        g2d.setColor(Color.BLACK);	
        g2d.drawString("PLAY!", getWidth()/2-110, getHeight()/2+60+300);
        g2d.drawImage(WingspanImage, getWidth()/2-200, 400, null);
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if((x >= getWidth()/2-200 && x <= getWidth()/2+100) && (y >=getHeight()/2+300 && y <= getHeight()/2+400)) {
			setVisible(false);
			try
			{
				getParent().add(new SetupPanel());
			}
			catch (Exception ex)
			{
				System.out.println("Failed to switch to SetupPanel");
			}
			getParent().repaint();
			getParent().remove(this);
			System.out.println("Successfully transitioned from IntroPanel to SetupPanel");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
