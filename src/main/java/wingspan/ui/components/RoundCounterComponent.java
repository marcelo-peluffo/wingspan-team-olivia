package wingspan.ui.components;
import java.awt.*;
import javax.swing.*;
import wingspan.core.GameState;

public class RoundCounterComponent extends JPanel{
    public RoundCounterComponent()
    {

    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 290, 140);
        BasicStroke stroke = new BasicStroke(5);
        g2.setStroke(stroke);
        g2.setColor(Color.BLACK);
        g2.drawRect(-10, -10, 300, 150);
        g2.drawLine(-10, 80, 290, 80);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        g2.drawString("Round " + GameState.roundNum + "/4", 20, 50);
        g2.setFont(new Font("Arial", Font.BOLD, 23));
        g2.drawString("Action Tokens Left: " + GameState.activePlayer.getActionsRemaining(), 20, 115);
    }
}
