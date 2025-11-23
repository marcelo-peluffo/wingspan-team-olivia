import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class ScrollContentPanel extends JPanel {

    private final int BOX_WIDTH = 200;
    private final int BOX_HEIGHT = 300;
    private final int BOXES = 25;
    private final int PER_ROW = 4;
    private final int GAP = 20;

    public ScrollContentPanel() {
        int rows = (int)Math.ceil(BOXES / (double)PER_ROW);

        int totalWidth = (BOX_WIDTH + GAP) * PER_ROW + GAP;
        int totalHeight = (BOX_HEIGHT + GAP) * rows + GAP;

        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setOpaque(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        for (int i = 0; i < BOXES; i++) {

            int row = i / PER_ROW;
            int col = i % PER_ROW;

            int x = GAP + col * (BOX_WIDTH + GAP);
            int y = GAP + row * (BOX_HEIGHT + GAP);
            
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect(x, y, BOX_WIDTH, BOX_HEIGHT);

            g2d.drawString("Bird " + (i + 1), x + 70, y + 30);
        }
    }
    
    private void handleClick(int x, int y) {

        for (int i = 0; i < BOXES; i++) {
            int col = i % PER_ROW;
            int row = i / PER_ROW;

            int bx = GAP + col * (BOX_WIDTH + GAP);
            int by = GAP + row * (BOX_HEIGHT + GAP);

            if (x >= bx && x <= bx + BOX_WIDTH &&
                y >= by && y <= by + BOX_HEIGHT) {

                System.out.println("Clicked slot: " + (i + 1)); // this is where u should edit mostly, when u click, this is where the changes occur (as of now, printing to the console the slot number)
                return;
            }
        }
    }

}
