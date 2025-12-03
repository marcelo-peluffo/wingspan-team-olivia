package wingspan.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import wingspan.cards.goals.Goal;
import wingspan.core.*;
import wingspan.cards.bonusCards.*;
import wingspan.utils.Pair;

import wingspan.cards.*;

import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameEndPanel extends JPanel implements KeyListener, MouseListener {

	private BufferedImage scoreSheetImage, background, goalBoardImage, boardImage;
    private Player winner;
    private ArrayList<int[]> scores;
    private int playerIndex;
    private Goal[] goals;
    private HashMap<Card, Pair> cardPositions;
    private HashMap<BonusCard, Pair> bonusCardPositions;
    private List<Card> forestCards;
    private List<Card> grasslandsCards;
    private List<Card> wetlandsCards;
    private final int CARD_WIDTH = 125;
    private final int CARD_HEIGHT = 200;
    private final int HAND_CARD_HEIGHT = 180;
    private final int HAND_CARD_WIDTH = 120;
    private BufferedImage displayedCard;
    private Card displayedCardInfo;

	
	public GameEndPanel() {
		try {
			scoreSheetImage = ImageIO.read(GameEndPanel.class.getResource("/Images/ScoreSheet.jpg"));
			background = ImageIO.read(GameEndPanel.class.getResource("/Images/backgroundImage2.jpeg"));
            goalBoardImage = ImageIO.read(GameEndPanel.class.getResource("/Images/goalBoard.jpg"));
            boardImage = ImageIO.read(GameEndPanel.class.getResource("/Images/GameBoard.jpg"));

		}catch (Exception e) {
			System.out.println("Error");
			return;
        }
        scores = new ArrayList<>();
        for(Player p: GameState.players)
        {
            scores.add(p.getAllScores());
        }
        int highest = -1;
        for(int i=0; i<4; i++)
        {
            if (scores.get(i)[6] > highest)
            {
                highest = scores.get(i)[6];
                winner = GameState.players.get(i);
            }
        }
        playerIndex = -2;
        goals = GameState.goalBoard.getGoals();
        displayedCard = null;
        displayedCardInfo = null;
        cardPositions = new HashMap<>();
        bonusCardPositions = new HashMap<>();

        addMouseListener(this);
		addKeyListener(this);
	}
	
	public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0,0, 1920, 1080, null);
        if (playerIndex == -2)
        {
            g2d.drawImage(scoreSheetImage, 500, 150, 900, 700,null);
            g2d.setColor(Color.YELLOW);
            g2d.fillRect(675, 50, 500, 75);
            g2d.setColor(Color.black);
            Font current = g2d.getFont();
            Font newFont = current.deriveFont(40F);
            g2d.setFont(newFont);
            g2d.drawString("Player " + (GameState.players.indexOf(winner) + 1 ) + " has won!!", 725, 100);
            g2d.setColor(new Color(19,175,87));
            g2d.fillRect(600, 890, 700, 50);
            g2d.setColor(Color.white);
            g2d.drawString("Press \"r\" to play again", 775, 925);
            g2d.setColor(Color.GRAY);
            g2d.fillRect(500, 950, 900, 50);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Press 't' to switch between this screen, the goal board, and the players' boards", 600, 980);
            int xPos = 825;
            int yPos = 275;
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            for(int i=0; i<4; i++)
            {
                int[] playerScores = scores.get(i);
                for(int score: playerScores)
                {
                    g2d.drawString("" + score, xPos, yPos);
                    yPos += 90;
                }
                yPos = 275;
                xPos += 120;
            }
            xPos = 793;
            yPos = 195;
            for(int i=1; i<5; i++)
            {
                g2d.drawString("Player " + i, xPos, yPos);
                xPos += 120;
            }
        }
        else if (playerIndex == -1)
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

            //draw instructions
            g2d.setColor(Color.GRAY);
            g2d.fillRect(500, 950, 900, 50);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("Press 't' to switch again", 705, 980);
        }
        else
        {
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
            int leftEnd;
            int x1;
            if(GameState.players.get(playerIndex).getBonusCards().size() % 2 == 0){
                leftEnd = Math.max(getWidth()/2 - (5 + HAND_CARD_WIDTH) - ((GameState.players.get(playerIndex).getBonusCards().size()/2 - 1) * (CARD_WIDTH + 10)), 350);
            }
            else {
                leftEnd = Math.max(getWidth()/2 - HAND_CARD_WIDTH/2 - (((GameState.players.get(playerIndex).getBonusCards().size() + 1)/2 - 1) * (CARD_WIDTH + 10)), 350);
            }
            x1 = leftEnd;
            for(BonusCard c: GameState.players.get(playerIndex).getBonusCards()){
                g.drawImage(c.getImage(), x1, 890, HAND_CARD_WIDTH, HAND_CARD_HEIGHT, null);
                bonusCardPositions.put(c, new Pair(x1, 890));
                x1 += (10 + HAND_CARD_WIDTH) * ((40 - GameState.players.get(playerIndex).getBonusCards().size()) / (50.0 - (20 - GameState.players.get(playerIndex).getBonusCards().size())));
            }
        }
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
        if (c == 't')
        {
            cardPositions.clear();
            bonusCardPositions.clear();
            playerIndex++;
            if (playerIndex == 4)
                playerIndex = -2;
            if (playerIndex >= 0)
            {
                getPlayerCards(GameState.players.get(playerIndex));
            }
        }
        if (c == 'r')
        {
            DataTable.initialize();
		    GameState.initialize();
		    for(int i=0; i<4; i++)
		    {
                try
                {
                    GameState.players.add(new Player());
                }
			    catch (Exception ex)
                {

                }
		    }
            setVisible(false);
            try
            {
                getParent().add(new SetupPanel());
            }
            catch (Exception ex)
            {
                
            }
            getParent().repaint();
            getParent().remove(this);
        }
        repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
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

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (playerIndex >= 0)
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
            for(BonusCard c: bonusCardPositions.keySet()){
            Pair p = bonusCardPositions.get(c);
            if (GameState.players.get(playerIndex).getHand().size() < 8)
            {
                if (x >= p.getX() && x <= p.getX() + CARD_WIDTH && y >= p.getY() && y <= p.getY() + CARD_HEIGHT)
                {
                    displayedCard = c.getImage();
                    displayedCardInfo = null;
                    break;
                }
            }
            else
            {
                double spaceBetweenCards = (10 + CARD_WIDTH) * ((40 - GameState.players.get(playerIndex).getHand().size()) / (50.0 - (20 - GameState.players.get(playerIndex).getHand().size())));
                if (x >= p.getX() && x < p.getX() + spaceBetweenCards && y >= p.getY() && y < p.getY() + CARD_HEIGHT)
                {
                    displayedCard = c.getImage();
                    displayedCardInfo = null;
                    break;
                }
            }
        }
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}