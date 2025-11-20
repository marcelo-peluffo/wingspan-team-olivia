import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class EndOfGamePanel extends JPanel implements KeyListener {

	private BufferedImage ep1, background;
	
	public EndOfGamePanel() {
		try {
			ep1 = ImageIO.read(EndOfGamePanel.class.getResource("/Image/FinalScreen.jpg"));
			background = ImageIO.read(EndOfGamePanel.class.getResource("/Image/backgroundImage2.jpeg"));

		}catch (Exception e) {
			System.out.println("Error");
			return;
		}	
		addKeyListener(this);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		
		g2d.drawImage(background, 0,0, 1920, 1080, null);
		g2d.drawImage(ep1, 400, 150, 900, 700,null);
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(575, 50, 500, 75);
		g2d.setColor(Color.black);
		Font current = g2d.getFont();
		Font newFont = current.deriveFont(40F);
		g2d.setFont(newFont);
		g2d.drawString("Player N has won!!", 600, 100);
		g2d.setColor(Color.white);
		g2d.drawString("Press \"r\" to play again", 700, 950);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		char a = e.getKeyChar();
		if(a == 'r') {
			System.out.println("Restart");
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
