package wingspan.ui;
import wingspan.cards.goals.Goal;
import wingspan.core.*;
import wingspan.enums.Food;
import wingspan.ui.components.*;
import wingspan.utils.Pair;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
//import java.io.IOException;
import java.util.Map;
import wingspan.cards.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements MouseListener{

    private Goal[] goals;
    private Player activePlayer;
    private Map<Food, Integer> foodInventory;
    private Map<Food, BufferedImage> foodToImage;
    private BufferedImage boardImage;
    private List<Card> forestCards;
    private List<Card> grasslandsCards;
    private List<Card> wetlandsCards;
    private BufferedImage displayedCard;
    private HashMap<Card, Pair> cardPositions; // this map will store all positions of the cards, and the card they're associated with
    private final int CARD_WIDTH = 125;
    private final int CARD_HEIGHT = 200;
    private int playerIndex;

    public MainPanel() throws IOException{
        goals = GameState.goalBoard.getGoals();
        activePlayer = GameState.activePlayer;
        foodInventory = activePlayer.getFoodInventory();
        foodToImage = new HashMap<>();

        setPreferredSize(new Dimension(150, 300));

        try {
            // Ensure these paths match your project structure
            foodToImage.put(Food.BERRY, ImageIO.read(getClass().getResourceAsStream("/Images/BerryToken.png")));
            foodToImage.put(Food.FISH, ImageIO.read(getClass().getResourceAsStream("/Images/FishToken.png")));
            foodToImage.put(Food.INVERTEBRATE, ImageIO.read(getClass().getResourceAsStream("/Images/InvertebrateToken.png")));
            foodToImage.put(Food.RODENT, ImageIO.read(getClass().getResourceAsStream("/Images/RodentToken.png")));
            foodToImage.put(Food.WHEAT, ImageIO.read(getClass().getResourceAsStream("/Images/WheatToken.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        boardImage = ImageIO.read(GameBoardComponent.class.getResource("/Images/GameBoard.jpg"));
        cardPositions = new HashMap<Card, Pair>();
        displayedCard = null;
        getPlayerCards(GameState.activePlayer);
        playerIndex = GameState.players.indexOf(GameState.activePlayer);
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

        //draw the food inventory
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int numItems = foodInventory.size();
        if (numItems == 0) return;

        int padding = 10;
        int x = padding;
        int panelWidth = getWidth();
        
        // Calculate heights
        int slotHeight = (getHeight() - 600 - padding * (numItems + 1)) / numItems;
        int imgSize = Math.min(slotHeight, panelWidth / 2);

        // --- Calculate Coordinates for Lines ---
        int startY = padding + 200;
        // The bottom Y is the top padding + the space taken by (N-1) items + the height of the last image
        int endY = startY + ((numItems - 1) * (slotHeight + padding)) + imgSize;
        
        // Place the vertical line to the right of the images
        int lineX = x + imgSize + 10; 
        
        // --- Draw the Black Lines (The Bracket) ---
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Make the line 2px thick for visibility

        // 1. Vertical Line
        g2.drawLine(lineX, startY, lineX, endY);
        
        // 2. Top Horizontal Cap (from left of image to the vertical line)
        g2.drawLine(x, startY, lineX + 20, startY);
        
        // 3. Bottom Horizontal Cap
        g2.drawLine(x, endY, lineX + 20, endY);

        // --- Draw Items ---
        int y = startY + 5;
        for (Food food : Food.values()) {
            if (!foodInventory.containsKey(food)) continue;

            BufferedImage img = foodToImage.get(food);
            int count = foodInventory.get(food);

            if (img != null) {
                // Draw Image
                g2.drawImage(img, x, y, imgSize, imgSize, null);

                // Draw Count Number
                // Calculate a font size relative to the image
                int fontSize = imgSize / 2;
                g2.setFont(new Font("Arial", Font.BOLD, fontSize));
                
                // Position text to the right of the vertical line
                int textX = lineX + 15; 
                
                // Center text vertically relative to the image
                // (y + imgSize/2) is center, + (fontSize/3) roughly centers the text baseline
                int textY = y + (imgSize / 2) + (fontSize / 3);
                
                g2.drawString(String.valueOf(count), textX, textY);

                y += slotHeight + padding;
            }
        }

        //draw the game board
        cardPositions.clear();
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.BLACK);
        String playerString = "Player " + (playerIndex + 1);
        if (GameState.players.get(playerIndex) != GameState.activePlayer)
        {
            playerString += " (Viewing)";
        }
        g.drawString(playerString, getWidth() / 2 - 100, 50);
        g.drawImage(boardImage, 350, 175, (int)(getWidth() * 0.6), (int)(getHeight() * 0.65), null);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(7));
        g2d.drawRect(getWidth() / 2 - 255, 120, 409, 50);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(getWidth() / 2 - 251, 124, 402, 51);
        g2d.setColor(Color.BLACK);
        g2d.drawLine(getWidth() / 2 - 200, 127, getWidth() / 2 - 200, 172);
        g2d.drawLine(getWidth() / 2 + 100, 127, getWidth() / 2 + 100, 172);
        int[] xPoints2 = {getWidth() / 2 - 240, getWidth() / 2 - 220, getWidth() / 2 - 220};
        int[] yPoints2 = {150, 135, 165};
        g2d.fillPolygon(xPoints2, yPoints2, 3);
        int[] xPoints3 = {getWidth() / 2 + 140, getWidth() / 2 + 120, getWidth() / 2 + 120};
        int[] yPoints3 = {150, 135, 165};
        g2d.fillPolygon(xPoints3, yPoints3, 3);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Change board to view", getWidth() / 2 - 150, 155);
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
        int mouseX = e.getX();
        int mouseY = e.getY();
        for(Card c: cardPositions.keySet())
        { 
            Pair position = cardPositions.get(c);
            if (mouseX > position.getX() && mouseY > position.getY() && mouseX < position.getX() + CARD_WIDTH && mouseY < position.getY() + CARD_HEIGHT)
            {
                displayedCard = c.getCardImage();
                break;
            }
        }
        if (mouseX > getWidth() / 2 + 100 && mouseX < 1114 && mouseY > 127 && mouseY < 172)
        {
            playerIndex++;
            if (playerIndex == 4)
            {
                playerIndex = 0;
            }
            getPlayerCards(GameState.players.get(playerIndex));
        }
        if (mouseX > 705 && mouseX < getWidth() / 2 - 200 && mouseY > 127 && mouseY < 172)
        {
            playerIndex--;
            if (playerIndex == -1)
            {
                playerIndex = 3;
            }
            getPlayerCards(GameState.players.get(playerIndex));
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
    public void getPlayerCards(Player p)
    {
        forestCards = p.getGameBoard().getForest();
        grasslandsCards = p.getGameBoard().getGrasslands();
        wetlandsCards = p.getGameBoard().getWetlands();
    }
}
