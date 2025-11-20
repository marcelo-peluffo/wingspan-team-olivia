package wingspan.ui;
import wingspan.cards.goals.Goal;
import wingspan.core.*;
import wingspan.ui.components.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements MouseListener{

    private Goal[] goals;

    public MainPanel(){
        goals = GameState.goalBoard.getGoals();
    	addMouseListener(this);
    }

    public void paint(Graphics g)
    {
		super.paint(g);
        //draw the goals
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

        //draw the navigator
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

        //draw the round counter
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
