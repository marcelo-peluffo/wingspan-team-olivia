import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class perRoundPanel extends JPanel implements MouseListener {

	private BufferedImage background, ep1;
	
	public perRoundPanel() {
		try {
			background = ImageIO.read(perRoundPanel.class.getResource("/Image/backgroundImage2.jpeg"));
			ep1 = ImageIO.read(perRoundPanel.class.getResource("/Image/EndScreenPerRound.jpg"));
			
		}catch(Exception e) {
			System.out.println("Error");
			return;
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, 0,0, 1920, 1080, null);
		g2d.drawImage(ep1, 400, 150 ,900, 700, null);
		g2d.setColor(Color.black);
		Font current = g2d.getFont();
		Font newFont = current.deriveFont(70F);
		g2d.setFont(newFont);
		g2d.drawString("Round N results", 600, 100);
		g2d.setColor(new Color(197,235,8));
		g2d.fillRect(400, 875, 900, 50);
		newFont = current.deriveFont(40F);
		g2d.setColor(Color.black);
		g2d.setFont(newFont);
		g2d.drawString("Click here to proceed to the next round", 420, 919);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
