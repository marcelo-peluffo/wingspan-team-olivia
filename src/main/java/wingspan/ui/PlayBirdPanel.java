import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PlayBirdPanel extends JPanel implements MouseListener {

    private BufferedImage ep1;
    private JScrollPane scrollPane;

    public PlayBirdPanel() {
        setLayout(null); 

        try {
            ep1 = ImageIO.read(PlayBirdPanel.class.getResource("/Image/backgroundImage2.jpeg"));
        } catch (Exception e) {
            System.out.println("Error");
            return;
        }

        ScrollContentPanel content = new ScrollContentPanel();
        content.setBounds(0, 0, 1000, 1500); 

        scrollPane = new JScrollPane(content);
        scrollPane.setBounds((1920 / 2) - 525, 250, 1000, 600);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getHorizontalScrollBar().setOpaque(false);


        add(scrollPane);
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(ep1, 0, 0, 1920, 1080, null);

        Font currentfont = g2d.getFont();
        Font newFont = currentfont.deriveFont(30F);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(1620, 0, 250, 75);
        g2d.setColor(Color.black);
        g2d.setFont(newFont);
        g2d.drawString("Play a bird", 1640, 50);

        newFont = currentfont.deriveFont(70F);
        g2d.setFont(newFont);
        g2d.drawString("Player n", (1920 / 2) - 150, 100);

        newFont = currentfont.deriveFont(45F);
        g2d.setFont(newFont);
        g2d.drawString("Choose a bird to play", (1920 / 2) - 225, 200);
        
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRect(1460, 200, 400,500);
        
        g2d.setColor(new Color(197,235,8));
        g2d.fillRect(1460, 750, 400, 75);
        g2d.setColor(Color.white);
        g2d.drawString("Confirm", 1569, 800);
        
        g2d.setColor(Color.red);
        g2d.fillRect((1920/2)-300, 925, 500, 75);
        
        g2d.setColor(Color.white);
        g2d.drawString("Return", (1920/2)-125, 975);

    }
    
   // these mouseclick methods are ONLY applicable for confirm button and return button

    @Override
    public void mouseClicked(MouseEvent e) {
    	int x = e.getX();
    	int y = e.getY();
    	
    	if((x >= 1460 && x <= 1860) && (y>= 750 && y <= 815)) {
    		System.out.println("Confirm");
    	}
    	
    	if((x>=(1920/2)-300 && x<= (1920/2)+200) && (y >= 925 && y <= 1000)) {
    		System.out.println("Return");
    	}
    }    	
    @Override
    public void mousePressed(MouseEvent e) {}
    	
    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
