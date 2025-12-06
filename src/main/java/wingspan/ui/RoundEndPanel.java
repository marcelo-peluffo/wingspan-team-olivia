package wingspan.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import wingspan.core.*;
import wingspan.cards.goals.*;
import java.util.*;
import wingspan.utils.Pair;
import wingspan.cards.*;
import java.util.List;

public class RoundEndPanel extends JPanel implements MouseListener, KeyListener {

	private BufferedImage background, goalBoardImage;
    private Goal[] goals;
    private Goal activeGoal;
    private HashMap<Player, Pair> actionCubePos = new HashMap<>();
    private int playerIndex;
    private boolean addedScore;
    private HashMap<Card, Pair> cardPositions;
    private List<Card> forestCards;
    private List<Card> grasslandsCards;
    private List<Card> wetlandsCards;
    private BufferedImage boardImage;
    private final int CARD_WIDTH = 125;
    private final int CARD_HEIGHT = 200;
    private BufferedImage displayedCard;
    private Card displayedCardInfo;
	
	public RoundEndPanel() {
		try {
			background = ImageIO.read(RoundEndPanel.class.getResource("/Images/backgroundImage2.jpeg"));
			goalBoardImage = ImageIO.read(RoundEndPanel.class.getResource("/Images/GoalBoard.jpg"));
            boardImage = ImageIO.read(RoundEndPanel.class.getResource("/Images/GameBoard.jpg"));
			
		}catch(Exception e) {
			System.out.println("Error");
			return;
		}
        goals = GameState.goalBoard.getGoals();
        activeGoal = goals[GameState.roundNum-1];
        playerIndex = -1;
        addedScore = false;
        cardPositions = new HashMap<>();
        displayedCard = null;
        displayedCardInfo = null;
		addMouseListener(this);
        addKeyListener(this);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, 0,0, 1920, 1080, null);
        //draw the goalboard, next round button, and heading
        if (playerIndex == -1)
        {
            g2d.drawImage(goalBoardImage, 500, 150 ,900, 700, null);
            g2d.setColor(Color.black);
            Font current = g2d.getFont();
            Font newFont = current.deriveFont(70F);
            g2d.setFont(newFont);
            g2d.drawString("Round " + GameState.roundNum + " results", 700, 100);
            g2d.setColor(new Color(19,175,87));
            g2d.fillRect(500, 875, 900, 50);
            newFont = current.deriveFont(30F);
            g2d.setColor(Color.white);
            g2d.setFont(newFont);
            if (GameState.roundNum < 4)
            {
                g2d.drawString("Click here to proceed to the next round", 705, 915);
            }
            else
            {
                g2d.drawString("Click here to proceed to the final scoring", 705, 915);
            }
            int yPos = 165;
            int xPos = 570;
            //draw the goal icons
            for(Goal goal: goals)
            {
                g2d.drawImage(goal.getImage(), xPos, yPos, 160, 155, null);
                yPos += 167;
                xPos -= 2;
            }
            //draw the player action tokens in a position corresponding to their score for the goal
            for(HashMap<Player, Pair> map: GameState.goalBoardPositions)
            {
                for(Player p: map.keySet())
                {
                    Pair pos = map.get(p);
                    g2d.setColor(GameState.actionCubeColors.get(p));
                    g2d.fillRect(pos.getX(), pos.getY(), 30, 30);
                }
            }
            int[] numInEachSlot = {0, 0, 0, 0, 0, 0};
            for(Player p: GameState.players)
            {
                g2d.setColor(GameState.actionCubeColors.get(p));
                int score = activeGoal.getPlayerScore(p);
                xPos = 1300 - score * 85;
                yPos = 10 + GameState.roundNum * 170 + numInEachSlot[score] * 30;
                numInEachSlot[score]++;
                g2d.fillRect(xPos, yPos, 30, 30);
                actionCubePos.put(p, new Pair(xPos, yPos));
                if (!addedScore)
                {
                    p.addGoalScore(score);
                }
            }

            //draw instructions
            g2d.setColor(Color.GRAY);
            g2d.fillRect(500, 950, 900, 50);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Press 't' to switch between the players' boards and this screen", 705, 980);
        }
        else
        { //draw the player's board
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(GameState.actionCubeColors.get(GameState.players.get(playerIndex)));
            String playerString = "Player " + (playerIndex + 1);
            g.drawString(playerString, getWidth() / 2 - 100, 50);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.BLACK);
            g.drawString("Press 't' to switch again", getWidth() / 2 - 150, 75);
            cardPositions.clear();
            g.drawImage(boardImage, 350, 175, (int)(getWidth() * 0.6), (int)(getHeight() * 0.65), null);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            int xPos = 610;
            for(Card c: forestCards)
            {
                g.drawImage(c.getCardImage(), xPos, 210, CARD_WIDTH, CARD_HEIGHT, null);
                cardPositions.put(c, new Pair(xPos, 210));
                xPos += 175;
            }
            xPos = 610;
            for(Card c: grasslandsCards)
            {
                g.drawImage(c.getCardImage(), xPos, 425, CARD_WIDTH, CARD_HEIGHT, null);
                cardPositions.put(c, new Pair(xPos, 425));
                xPos += 175;
            }
            xPos = 610;
            for(Card c: wetlandsCards)
            {
                g.drawImage(c.getCardImage(), xPos, 640, CARD_WIDTH, CARD_HEIGHT, null);
                cardPositions.put(c, new Pair(xPos, 640));
                xPos += 175;
            }
            g.drawImage(displayedCard, 1600, 300, CARD_WIDTH * 2, CARD_HEIGHT * 2, null);
            if (displayedCardInfo != null)
            {
                g.setColor(Color.WHITE);
                g.drawString("Eggs: " + displayedCardInfo.getCurrentEggs() + "/" + displayedCardInfo.getBirdInfo().getMaxEggs(), 1600, 720);
                g.drawString("Cached Food Tokens: " + displayedCardInfo.getFoodTokens().size(), 1600, 740);
                g.drawString("Tucked Cards: " + displayedCardInfo.getTuckedCards().size(), 1600, 760);
            }
        }

        //draw the round counter component
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, 290, 75);
        BasicStroke stroke = new BasicStroke(5);
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(-10, -10, 300, 80);
        g2d.setFont(new Font("Arial", Font.BOLD, 50));
        g2d.drawString("Round End", 20, 50);
        addedScore = true;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		if (playerIndex == -1)
        {
            if((x >= 400 && x <= 1300) && (y >= 875 && y<= 925)) {
                GameState.roundNum++;
                GameState.goalBoardPositions.add(actionCubePos);
                GameState.cardManager.faceUpCards.clear();
                for(int i=0; i<3; i++)
                {
                    GameState.cardManager.faceUpCards.add(null);
                }
                GameState.cardManager.refillVisibleCards();
                for(Player p: GameState.players)
                {
                    p.resetActionsRemaining(GameState.roundNum);
                }
				if (roundNum < 5)
				{
					GameState.activePlayer = GameState.players.get(GameState.roundNum - 1);
				}
                setVisible(false);
                try
                {
                    if (GameState.roundNum <= 4)
                    {
                        getParent().add(new MainPanel());
                    }
                    else
                    {
                        getParent().add(new GameEndPanel());
                    }
                }
                catch (Exception ex)
                {

                }
                getParent().repaint();
                getParent().remove(this);
            }
        }
        else
        {
            for(Card c: cardPositions.keySet())
            {
                Pair pos = cardPositions.get(c);
                if (x > pos.getX() && x < pos.getX() + CARD_WIDTH && y > pos.getY() && y < pos.getY() + CARD_HEIGHT)
                {
                    displayedCard = c.getCardImage();
                    displayedCardInfo = c;
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

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 't')
        {
            displayedCard = null;
            displayedCardInfo = null;
            playerIndex++;
            if (playerIndex == 4)
            {
                playerIndex = -1;
            }
            if (playerIndex >= 0)
            {
                getPlayerCards(GameState.players.get(playerIndex));
            }
        }
        repaint();
    }
	
    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }

    public void getPlayerCards(Player p)
    {
        forestCards = p.getGameBoard().getForest();
        grasslandsCards = p.getGameBoard().getGrasslands();
        wetlandsCards = p.getGameBoard().getWetlands();
    }
}
