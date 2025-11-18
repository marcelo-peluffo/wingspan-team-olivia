package wingspan.ui.components;

import javax.swing.JPanel;
import java.awt.*;
import wingspan.cards.goals.*;
import wingspan.core.*;

public class GoalsComponent extends JPanel
{
    private Goal[] goals;
    public GoalsComponent()
    {
        goals = GameState.goalBoard.getGoals();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        int goalXPos = getWidth() - 400;
        for(Goal goal: goals)
        {
            g.drawImage(goal.getImage(), goalXPos, getHeight() - 100, 100, 100, null);
            goalXPos += 100;
        }
        int arrowXPos = getWidth() - 450 + GameState.roundNum * 100;
        int[] xPoints = {arrowXPos - 25, arrowXPos, arrowXPos + 25};
        int[] yPoints = {getHeight() - 155, getHeight() - 110, getHeight() - 155};
        g.fillPolygon(xPoints, yPoints, 3);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Goals", getWidth() - 250, getHeight() - 160);
    }
}