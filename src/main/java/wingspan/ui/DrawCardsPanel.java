package wingspan.ui;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import wingspan.cards.*;
import wingspan.cards.bonusCards.BonusCard;
import wingspan.cards.goals.Goal;
import wingspan.core.GameState;
import wingspan.core.Player;
import wingspan.enums.*;
import wingspan.utils.Pair;

public class DrawCardsPanel extends JPanel implements MouseListener, KeyListener{

	private BufferedImage background;
    private List<Card> faceUpCards;
    private Map<Food, Integer> foodInventory;
    private Map<Food, BufferedImage> foodToImage;
    private Player activePlayer;
    private boolean hasChoice;
    private int numChoices;
    private Goal[] goals;
    private Card chosenCard;
    private Map<Card, Pair> playerHandCardPositions;
    private Map<BonusCard, Pair> playerBonusCardPositions;
    private Map<Card, Pair> faceUpCardPositions;
    private final int FACE_UP_CARD_WIDTH = 300;
    private final int FACE_UP_CARD_HEIGHT = 450;
    private HashMap<Card, Pair> cardPositions;
    private final int HAND_CARD_HEIGHT = 180;
    private final int HAND_CARD_WIDTH = 120;
    private boolean displayBonus;
    private BufferedImage displayedCard;
    private boolean exchanging;
    private BufferedImage boardImage;
    private List<Card> forestCards;
    private List<Card> grasslandsCards;
    private List<Card> wetlandsCards;
    private final int CARD_WIDTH = 125;
    private final int CARD_HEIGHT = 200;
    private Card displayedCardInfo;
    private String headingText;
	
	public DrawCardsPanel(int numChoices, boolean hasChoice) {
		try {
			
			background = ImageIO.read(getClass().getResourceAsStream("/Images/backgroundImage2.jpeg"));
            boardImage = ImageIO.read(getClass().getResourceAsStream("/Images/GameBoard.jpg"));
			
		}catch(Exception e) {
			System.out.println("Error");
		}
        faceUpCards = GameState.cardManager.getFaceUpCards();
        activePlayer = GameState.activePlayer;
        foodInventory = activePlayer.getFoodInventory();
        foodToImage = new HashMap<>();
        goals = GameState.goalBoard.getGoals();

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
        this.numChoices = numChoices;
        this.hasChoice = hasChoice;
        chosenCard = null;
        faceUpCardPositions = new HashMap<>();
        playerHandCardPositions = new HashMap<>();
        playerBonusCardPositions = new HashMap<>();
        cardPositions = new HashMap<>();
        displayedCard = null;
        displayBonus = false;
        exchanging = false;
        displayedCardInfo = null;
        headingText = "Click on one of the three face up cards, or draw a random card";
		addMouseListener(this);
        addKeyListener(this);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        //draw the text at the top
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(GameState.actionCubeColors.get(GameState.activePlayer));
        String playerString = "Player " + (GameState.players.indexOf(GameState.activePlayer) + 1);
        g.drawString(playerString, getWidth() / 2 - 100, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString(headingText, getWidth() / 2 - 250, 80);
        if (!exchanging)
        {
            g.drawString("WARNING: You may not change your selection once you select a card", getWidth() / 2 - 250, 100);
        }

        //draw the 3 face up cards
        Graphics2D g2d = (Graphics2D)g;
        if (!exchanging)
        {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(4));
            g2d.drawLine(getWidth() / 2 - 400, getHeight() / 2 - 350, getWidth() / 2 + 400, getHeight() / 2 - 350);
            g2d.drawLine(getWidth() / 2 - 400, getHeight() / 2 - 350, getWidth() / 2 - 400, getHeight() / 2 - 250);
            g2d.drawLine(getWidth() / 2, getHeight() / 2 - 350, getWidth() / 2, getHeight() / 2 - 250);
            g2d.drawLine(getWidth() / 2 + 400, getHeight() / 2 - 350, getWidth() / 2 + 400, getHeight() / 2 - 250);
            int xPos = getWidth() / 2 - 550;
            for(Card c: faceUpCards)
            {
                if (c != null)
                {
                    g.drawImage(c.getCardImage(), xPos, getHeight() / 2 - 250, FACE_UP_CARD_WIDTH, FACE_UP_CARD_HEIGHT, null);
                    faceUpCardPositions.put(c, new Pair(xPos, getHeight() / 2 - 250));
                }
                xPos += 400;
            }
        }

        //draw the round counter
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 290, 140);
        BasicStroke stroke = new BasicStroke(5);
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(-10, -10, 300, 150);
        g2d.drawLine(-10, 80, 290, 80);
        g2d.setFont(new Font("Arial", Font.BOLD, 50));
        g2d.drawString("Round " + GameState.roundNum + "/4", 20, 50);
        g2d.setFont(new Font("Arial", Font.BOLD, 23));
        g2d.drawString("Action Tokens Left: " + GameState.activePlayer.getActionsRemaining(), 20, 115);

        //draw the food inventory
        Graphics2D g2 = (Graphics2D)g;
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
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(2)); // Make the line 2px thick for visibility

        // 1. Vertical Line
        g2.drawLine(lineX, startY, lineX, endY - 50);
        
        // 2. Top Horizontal Cap (from left of image to the vertical line)
        g2.drawLine(x, startY, lineX + 40, startY);
        
        // 3. Bottom Horizontal Cap
        g2.drawLine(x, endY - 50, lineX + 40, endY - 50);

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
                g2.setColor(Color.ORANGE);
                g2.drawString(String.valueOf(count), textX, textY);

                y += slotHeight + padding;
            }
        }

        //draw the goals
        g.setColor(Color.WHITE);
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
        
        //draw the upper right text
        g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(1670, 0, 250, 75);
		g2d.setColor(Color.black);
        Font currentfont = g2d.getFont();
		Font newFont = currentfont.deriveFont(30F);
		g2d.setFont(newFont);
		g2d.drawString("Drawing cards", 1690, 50);

        //draw the random card button
        if (!exchanging)
        {
            g2d.setColor(new Color(197,235,8));
            g2d.fillRect((1920/2)-250, 800, 400, 50); 
            g2d.setColor(Color.black);
            g2d.drawString("Draw a random card", (1920/2)-200, 840);
        }
		

		//draw the confirm card interface
        if (chosenCard != null)
        {
            g.drawImage(chosenCard.getCardImage(), 1600, 300, 250, 400, null);
            g2d.setColor(new Color(19,175,87));
		    g2d.fillRect(1620, 720, 200, 50); //
		    g2d.setColor(Color.black);
		    g2d.drawString("Confirm", 1665, 755);
        }

        //draw the bottom left interface
        if (!exchanging)
        {
            g2d.setColor(Color.WHITE);
            if (!hasChoice)
            {
                g2d.drawString("Remaining choices: " + numChoices, 30, getHeight() - 25);
            }
            else
            {
                boolean canExchange = GameState.activePlayer.getTotalEggsAmount() > 0;
                g2d.drawString("Remaining choices: " + numChoices, 30, getHeight() - 110);
                if (canExchange)
                    g2d.setColor(Color.ORANGE);
                else
                    g2d.setColor(Color.GRAY);
                g2d.fillRect(30, getHeight() - 90, 300, 75);
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.PLAIN, 20));
                g2d.drawString("Exchange egg -> extra choice", 40, getHeight() - 50);
            }
        }
        
        //draw the player's cards
        int leftEnd;
        int x1;
        if (!displayBonus)
        {
            if(GameState.activePlayer.getHand().size() % 2 == 0){
            leftEnd = Math.max(getWidth()/2 - (5 + HAND_CARD_WIDTH) - ((GameState.activePlayer.getHand().size()/2 - 1) * (HAND_CARD_WIDTH + 10)), 350);
            }
            else {
                leftEnd = Math.max(getWidth()/2 - HAND_CARD_WIDTH/2 - (((GameState.activePlayer.getHand().size() + 1)/2 - 1) * (HAND_CARD_WIDTH + 10)), 350);
            }
        }
        else
        {
            if(GameState.activePlayer.getBonusCards().size() % 2 == 0){
            leftEnd = Math.max(getWidth()/2 - (5 + HAND_CARD_WIDTH) - ((GameState.activePlayer.getBonusCards().size()/2 - 1) * (HAND_CARD_WIDTH + 10)), 350);
            }
            else {
                leftEnd = Math.max(getWidth()/2 - HAND_CARD_WIDTH/2 - (((GameState.activePlayer.getBonusCards().size() + 1)/2 - 1) * (HAND_CARD_WIDTH + 10)), 350);
            }
        }

        x1 = leftEnd;
        if (!displayBonus)
        {
            for(Card c: GameState.activePlayer.getHand()){
                g.drawImage(c.getCardImage(), x1, 890, HAND_CARD_WIDTH, HAND_CARD_HEIGHT, null);
                playerHandCardPositions.put(c, new Pair(x1, 890));
                x1 += (10 + HAND_CARD_WIDTH) * ((40 - GameState.activePlayer.getHand().size()) / (50.0 - (20 - GameState.activePlayer.getHand().size())));
            }
        }
        else
        {
            for(BonusCard c: GameState.activePlayer.getBonusCards()){
                g.drawImage(c.getImage(), x1, 890, HAND_CARD_WIDTH, HAND_CARD_HEIGHT, null);
                playerBonusCardPositions.put(c, new Pair(x1, 890));
                x1 += (10 + HAND_CARD_WIDTH) * ((40 - GameState.activePlayer.getBonusCards().size()) / (50.0 - (20 - GameState.activePlayer.getBonusCards().size())));
            }
        }
        g.drawImage(displayedCard, 1600, 300, 250, 400, null);

        if (exchanging)
        {
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
                if (displayedCardInfo.getCurrentEggs() > 0)
                {
                    g.setFont(new Font("Arial", Font.PLAIN, 20));
                    g.drawString("Exchange using this card?", 1625, 750);
                    g.drawString("(Press 'y' or 'n')", 1625, 770);
                }
                
            }
            
        }
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
        if (!exchanging)
        {
            if (chosenCard == null)
            {
                for(Card c: faceUpCardPositions.keySet())
                {
                    Pair pos = faceUpCardPositions.get(c);
                    if (x > pos.getX() && y > pos.getY() && x < pos.getX() + FACE_UP_CARD_WIDTH && y < pos.getY() + FACE_UP_CARD_HEIGHT)
                    {
                        chosenCard = c;
                        break;
                    }
                }
                if (x > (1920/2)-250 && y > 800 && x < ((1920 / 2) - 250 + 840) && y < 850)
                {
                    chosenCard = GameState.cardManager.getRandomCard();
                }
            if (!displayBonus)
                {
                for(Card c: playerHandCardPositions.keySet()){
                Pair p = playerHandCardPositions.get(c);
                if (GameState.activePlayer.getHand().size() < 8)
                {
                    if (x >= p.getX() && x <= p.getX() + HAND_CARD_WIDTH && y >= p.getY() && y <= p.getY() + HAND_CARD_HEIGHT)
                    {
                        displayedCard = c.getCardImage();
                        break;
                    }
                }
                else
                {
                    double spaceBetweenCards = (10 + HAND_CARD_WIDTH) * ((40 - GameState.activePlayer.getHand().size()) / (50.0 - (20 - GameState.activePlayer.getHand().size())));
                    if (x >= p.getX() && x < p.getX() + spaceBetweenCards && y >= p.getY() && y < p.getY() + HAND_CARD_HEIGHT)
                    {
                        displayedCard = c.getCardImage();
                        break;
                    }
                }
                }
                }
            else
            {
                for(BonusCard c: playerBonusCardPositions.keySet()){
                Pair p = playerBonusCardPositions.get(c);
                if (GameState.activePlayer.getBonusCards().size() < 8)
                {
                    if (x >= p.getX() && x <= p.getX() + HAND_CARD_WIDTH && y >= p.getY() && y <= p.getY() + HAND_CARD_HEIGHT)
                    {
                        displayedCard = c.getImage();
                        break;
                    }
                }
                else
                {
                    double spaceBetweenCards = (10 + HAND_CARD_WIDTH) * ((40 - GameState.activePlayer.getBonusCards().size()) / (50.0 - (20 - GameState.activePlayer.getBonusCards().size())));
                    if (x >= p.getX() && x < p.getX() + spaceBetweenCards && y >= p.getY() && y < p.getY() + HAND_CARD_HEIGHT)
                    {
                        displayedCard = c.getImage();
                        break;
                    }
                }
            }
            }
            if (x > 30 && x < 330 && y > getHeight() - 90 && y < getHeight() - 15)
            {
                if (GameState.activePlayer.getTotalEggsAmount() > 0)
                {
                    exchanging = true;
                    forestCards = GameState.activePlayer.getGameBoard().getForest();
                    grasslandsCards = GameState.activePlayer.getGameBoard().getGrasslands();
                    wetlandsCards = GameState.activePlayer.getGameBoard().getWetlands();
                    hasChoice = false;
                    numChoices++;
                    headingText = "Click on any card to exchange one of its eggs for an extra choice";
                }
            }
            }
            else
            {
                if (x > 1620 && y > 720 && x < 1820 && y < 770)
                {
                    numChoices--;
                    GameState.activePlayer.addCard(chosenCard);
                    if (faceUpCards.contains(chosenCard))
                    {
                        faceUpCards.set(faceUpCards.indexOf(chosenCard), null);
                    }
                    chosenCard = null;
                    if (numChoices == 0)
                    {
                        GameState.cardManager.refillVisibleCards();
                        wetlandsCards = GameState.activePlayer.getGameBoard().getWetlands();
                        for(int i=wetlandsCards.size()-1; i>=0; i--)
                        {
                            if (wetlandsCards.get(i).getBirdInfo().getPowerColor() == PowerColor.BROWN)
                            {
                                setVisible(false);
                                getParent().add(new AbilityPanel(GameState.activePlayer, wetlandsCards.get(i), Habitat.WETLANDS));
                                getParent().repaint();
                                getParent().remove(this);
                                return;
                            }
                        }
                        boolean roundEnd = false;
                        GameState.activePlayer.decreaseActionsRemaining();
                        int playerIndex = GameState.players.indexOf(activePlayer);
                        if (playerIndex < 3)
                        {
                            if (GameState.players.get(playerIndex + 1).getActionsRemaining() > 0)
                            {
                                GameState.activePlayer = GameState.players.get(playerIndex + 1);
                            }
                            else
                            {
                                roundEnd = true;
                            }
                        }
                        else
                        {
                            if (GameState.players.get(0).getActionsRemaining() > 0)
                            {
                                GameState.activePlayer = GameState.players.get(0);
                            }
                            else
                            {
                                roundEnd = true;
                            }
                        }
                        setVisible(false);
                        try
                        {
                            if (!roundEnd)
                                getParent().add(new MainPanel());
                            else
                                getParent().add(new RoundEndPanel());
                        }
                        catch (Exception ex)
                        {
                            System.out.println("Failed to load MainPanel");
                        }
                        getParent().repaint();
                        getParent().remove(this);
                    }
                }
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
                    break;
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
            displayBonus = !displayBonus;
        }
        if (exchanging)
        {
            if (displayedCardInfo != null && displayedCardInfo.getCurrentEggs() > 0)
            {
                if (c == 'y')
                {
                    displayedCardInfo.removeEggs(1);
                    exchanging = false;
                    displayedCard = null;
                    displayedCardInfo = null;
                    headingText = "Click on one of the three face up cards, or draw a random card";
                }
                else if (c == 'n')
                {
                    displayedCard = null;
                    displayedCardInfo = null;
                }
            }
        }
        repaint();
    }
	
    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
	
}