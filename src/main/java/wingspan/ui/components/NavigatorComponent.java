package wingspan.ui.components;

import javax.swing.JPanel;
import java.awt.*;
import wingspan.cards.goals.*;
import wingspan.core.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NavigatorComponent extends JPanel implements MouseListener
{
    public NavigatorComponent()
    {
        addMouseListener(this);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(6));
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(-10, getHeight() - 350, 250, 355);
        g2d.setColor(new Color(26, 176, 0));
        if (GameState.chosenView.equals("GameBoard"))
        {
            g2d.fillRect(-10, getHeight()-350, 250, 116);
        }
        else if (GameState.chosenView.equals("BirdFeeder"))
        {
            g2d.fillRect(-10, getHeight()-233, 250, 116);
        }
        else
        {
            g2d.fillRect(-10, getHeight()-116, 250, 116);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawRect(-10, getHeight() - 350, 250, 355);
        g2d.drawLine(-10, getHeight() - 233, 240, getHeight() - 233);
        g2d.drawLine(-10, getHeight() - 116, 240, getHeight() - 116);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Game Board", 30, getHeight()-285);
        g2d.drawString("Bird Feeder", 30, getHeight() - 169);
        g2d.drawString("Face Up", 30, getHeight() - 63);
        g2d.drawString("Bird Cards", 30, getHeight()-33);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (x < 240 && y > getHeight() - 350)
        {
            {
                if (y < getHeight() - 233)
                {
                    GameState.chosenView = "GameBoard";
                }
                else if (y < getHeight() - 116)
                {
                    GameState.chosenView = "BirdFeeder";
                }
                else
                {
                    GameState.chosenView = "AvaliableCards";
                }
            }
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}