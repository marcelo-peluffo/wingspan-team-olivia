import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
public class DrawCardPanel extends JPanel implements MouseListener{
	private BufferedImage ep1;
	
	public DrawCardPanel() {
		try {
			
			ep1 = ImageIO.read(DrawCardPanel.class.getResource("/Image/BackgroundImage2.jpeg"));
			//ep1 = resize(ep1, 1920, 1080);
			
		}catch(Exception e) {
			System.out.println("Error");
			return;
		}
		
		addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(ep1, 0, 0, 1920, 1080, null);
		Font currentfont = g2d.getFont();
		Font newFont = currentfont.deriveFont(70F);
		g2d.setFont(newFont);
		g2d.drawString("Player n", (1920/2)-150, 100);
		g2d.fillRect(500, 300, 200, 300);
		g2d.fillRect(800, 300, 200, 300);
		g2d.fillRect(1100, 300, 200, 300);
		g2d.fillRect(1520, 400, 200,300);
		
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(1620, 0, 250, 75);
		g2d.setColor(Color.black);
		newFont = currentfont.deriveFont(30F);
		g2d.setFont(newFont);
		g2d.drawString("Drawing cards", 1640, 50);
		g2d.setColor(new Color(197,235,8));
		g2d.fillRect((1920/2)-250, 700, 400, 50); 
		g2d.setColor(Color.black);
		g2d.drawString("Draw a random card", (1920/2)-200, 740);
		g2d.setColor(new Color(19,175,87));
		g2d.fillRect(1520, 720, 200, 50); //
		g2d.setColor(Color.black);
		g2d.drawString("Confirm", 1575, 755);
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if((x >= 500 && x <= 700) && (y >= 300 && y <= 600)) {
			System.out.println("First option chosen");
			// place code here
		}else if((x >= 800 && x <= 1000)&& (y >= 300 && y<= 600)) {
			System.out.println("Second option chosen");
			// place code here
		}else if((x >= 1100 && x <= 1300) && (y >= 300 && y <= 600)) {
			System.out.println("Third option chosen");
		}else if((x >= 1520 && x <= 1720) && (y >= 720 && y <= 770)) {
			System.out.println("Card confirmed!!");
			// place code here
		}else if((x >= (1920/2)-250 && x <= (1920/2)+400) && (y>= 700 && y <= 750)) {
			Random rand = new Random();
			//System.out.println("Random");
			//Place code for random card chosen based of num
			int num = rand.nextInt(3)+1;
			System.out.println("Random card- " + num);
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

