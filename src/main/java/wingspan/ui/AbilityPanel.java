package wingspan.ui;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.image.BufferedImage;
import wingspan.cards.bonusCards.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import wingspan.utils.Pair;
import wingspan.cards.*;
import wingspan.cards.goals.Goal;
import wingspan.core.GameState;
import wingspan.core.Player;
import wingspan.enums.*;
import wingspan.food.*;

public class AbilityPanel extends JPanel implements KeyListener, MouseListener{
    private BufferedImage boardImage;
    private List<Card> forestCards;
    private List<Card> grasslandsCards;
    private List<Card> wetlandsCards;
    private BufferedImage displayedCard;
    private HashMap<Card, Pair> cardPositions; // this map will store all positions of the cards, and the card they're associated with
    private final int CARD_WIDTH = 125;
    private final int CARD_HEIGHT = 200;
    private final int HAND_CARD_HEIGHT = 180;
    private final int HAND_CARD_WIDTH = 120;
    private Player player;
    private Map<Food, Integer> foodInventory;
    private Map<Food, BufferedImage> foodToImage;
    private BufferedImage background;
    private Card activationCard;
    private boolean hasActivated;
    private Habitat cardHabitat;
    private Goal[] goals;
    private boolean hasExecuted;
    private String abilityType;
    private boolean actionWasSuccessful;
    private HashMap<BonusCard, Pair> bonusCardsPos;
    private boolean hasSelectedBonus;
    private BonusCard chosenBonusCard;

    public AbilityPanel(Player p, Card activationCard, Habitat cardHabitat)
    {
        try
        {
            boardImage = ImageIO.read(AbilityPanel.class.getResource("/Images/GameBoard.jpg"));
            background = ImageIO.read(AbilityPanel.class.getResource("/Images/backgroundImage2.jpeg"));
        }
        catch (Exception e)
        {
            System.out.println("Error loading board image");
        }
        GameState.activeCard = activationCard;
        cardPositions = new HashMap<Card, Pair>();
        displayedCard = null;
        getPlayerCards(GameState.activePlayer);
        player = p;
        foodInventory = p.getFoodInventory();
        foodToImage = new HashMap<>();
        this.activationCard = activationCard;
        hasActivated = false;
        this.cardHabitat = cardHabitat;
        goals = GameState.goalBoard.getGoals();
        bonusCardsPos = new HashMap<>();
        hasSelectedBonus = false;

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
        abilityType = activationCard.getBirdInfo().getBehavior().describe();
        addKeyListener(this);
        addMouseListener(this);
    }

    public void drawAbilityUI(Graphics g) // draw UI prompting the player for input if needed for the ability. If no input is needed, directly use executePower here
    {
        if (abilityType.equals("FoodCacheBehavior"))
        {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString("Cache the food if avaliable?", 1600, 300 + CARD_HEIGHT * 2 + 25);
            g.drawString("(Press 'y' or 'n')", 1600, 300 + CARD_HEIGHT * 2 + 50);
        }
        else if (abilityType.equals("BonusCardBehavior"))
        {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            String s = "Click one of the bonus cards to keep it";
            if (hasSelectedBonus)
                s = "Press 'c' to confirm";
            g.drawString(s, 1600, 300 + CARD_HEIGHT * 2 + 25);
            for(BonusCard c: bonusCardsPos.keySet())
            {
                Pair p = bonusCardsPos.get(c);
                g.drawImage(c.getImage(), p.getX(), p.getY(), HAND_CARD_WIDTH, HAND_CARD_HEIGHT, null);
            }
        }
        else if (abilityType.equals("RollDiceBehavior"))
        {
            g.setColor(new Color(19,175,87));
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.fillRect(1600, 300 + CARD_HEIGHT * 2 + 25, 250, 50);
            g.setColor(Color.WHITE);
            g.drawString("Roll Dice", 1675, 300 + CARD_HEIGHT * 2 + 50);
            ArrayList<FoodDice> discardedDice = GameState.foodManager.getUsedDice();
            int xPos = 1600;
            for(FoodDice fd: discardedDice)
            {
                g.drawImage(fd.getImage(), xPos, 300 + CARD_HEIGHT * 2 + 100, 50, 50, null);
            }

        }
        else if (abilityType.equals("GainFoodAllBehavior") || abilityType.equals("GainFoodBehavior") || abilityType.equals("CacheBehavior"))
        {
            activationCard.getBirdInfo().getBehavior().executePower();
            hasExecuted = true;
            repaint();
        }
    }

    public void drawAbilityEndUI(Graphics g)
    {
        if (abilityType.equals("FoodCacheBehavior"))
        {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            String s = "";
            if (!actionWasSuccessful)
                s = "Bird feeder didn't have any wheat";
            else if (actionWasSuccessful && GameState.choseToCache)
                s = "Successfully cached 1 wheat token";
            else
                s = "Gained 1 wheat token";
            g.drawString(s, 1600, 300 + CARD_HEIGHT * 2 + 25);
            g.drawString("Press ENTER to proceed", 1600, 300 + CARD_HEIGHT * 2 + 50);
        }
        else if (abilityType.equals("GainFoodAllBehavior"))
        {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("All players gained 1 food", 1600, 300 + CARD_HEIGHT * 2 + 25);
            g.drawString("Press ENTER to proceed", 1600, 300 + CARD_HEIGHT * 2 + 50);
        }
        else if (abilityType.equals("GainFoodBehavior"))
        {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("You gained 1 food token", 1600, 300 + CARD_HEIGHT * 2 + 25);
            g.drawString("Press ENTER to proceed", 1600, 300 + CARD_HEIGHT * 2 + 50);
        }
        else if (abilityType.equals("CacheBehavior"))
        {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("Successfully cached 1 wheat token", 1600, 300 + CARD_HEIGHT * 2 + 25);
            g.drawString("Press ENTER to proceed", 1600, 300 + CARD_HEIGHT * 2 + 50);
        }
        else if (abilityType.equals("BonusCardBehavior"))
        {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.setColor(Color.WHITE);
            g.drawString("Added Bonus Card to hand", 1600, 300 + CARD_HEIGHT * 2 + 25);
            g.drawString("Press ENTER to proceed", 1600, 300 + CARD_HEIGHT * 2 + 50);
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        //draw game board
        cardPositions.clear();
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(GameState.actionCubeColors.get(player));
        String playerString = "Player " + (GameState.players.indexOf(player) + 1);
        g.drawString(playerString, getWidth() / 2 - 100, 50);
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

        //round counter component
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
        g2.drawString("Action Tokens Left: " + player.getActionsRemaining(), 20, 115);

        // food inventory component
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
        //draw player's hand
        
        int leftEnd;
        if(player.getHand().size() % 2 == 0){
            leftEnd = Math.max(getWidth()/2 - (5 + CARD_WIDTH) - ((player.getHand().size()/2 - 1) * (CARD_WIDTH + 10)), 350);
        }
        else {
            leftEnd = Math.max(getWidth()/2 - CARD_WIDTH/2 - (((player.getHand().size() + 1)/2 - 1) * (CARD_WIDTH + 10)), 350);
        }

        x = leftEnd;
        for(Card c: player.getHand()){
            g.drawImage(c.getCardImage(), x, 890, CARD_WIDTH, CARD_HEIGHT, null);
            cardPositions.put(c, new Pair(x, 890));
            x += (10 + CARD_WIDTH) * ((40 - player.getHand().size()) / (50.0 - (20 - player.getHand().size())));
        }

        //top right text
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(1670, 0, 250, 75);
		g2d.setColor(Color.black);
		Font newFont = new Font("Arial", Font.BOLD, 25);
		g2d.setFont(newFont);
		g2d.drawString("Activating Power", 1690, 50);
        //displayed card
        if (hasActivated && !hasExecuted && abilityType.equals("BonusCardBehavior") && hasSelectedBonus)
        {
            g.drawImage(displayedCard, 1600, 300, CARD_WIDTH * 2, CARD_HEIGHT * 2, null);
        }
        else
        {
            g.drawImage(activationCard.getCardImage(), 1600, 300, CARD_WIDTH * 2, CARD_HEIGHT * 2, null);
        }
        Pair pos = cardPositions.get(activationCard);
        if (activationCard.getBirdInfo().getPowerColor() == PowerColor.BROWN)
        {
            g2d.setColor(GameState.actionCubeColors.get(GameState.activePlayer));
            g2d.fillRect(pos.getX() + 50, pos.getY() + 50, 25, 25);
        }
        else
        {
            g2d.setStroke(new BasicStroke(5));
            g2d.setColor(new Color(0, 170, 150));
            g2d.drawRect(pos.getX(), pos.getY(), CARD_WIDTH, CARD_HEIGHT);
        }

        //draw activation prompt
        if (!hasActivated)
        {
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            g2d.setColor(Color.WHITE);
            g2d.drawString("Activate this card's ability?", 1600, 300 + CARD_HEIGHT * 2 + 25);
            g2d.drawString("(Press 'y' or 'n')", 1600, 300 + CARD_HEIGHT * 2 + 50);
        }
        else if (!hasExecuted)
        {
            drawAbilityUI(g);
        }
        else
        {
            drawAbilityEndUI(g);
        }

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

    public void getPlayerCards(Player p)
    {
        forestCards = p.getGameBoard().getForest();
        grasslandsCards = p.getGameBoard().getGrasslands();
        wetlandsCards = p.getGameBoard().getWetlands();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if (hasExecuted)
            {
                boolean end = false;
                if (activationCard.getBirdInfo().getPowerColor() == PowerColor.WHITE || activationCard.getBirdInfo().getPowerColor() == PowerColor.PINK)
                {
                    end = true;
                }
                else
                {
                    player.getGameBoard().setActiveHabitat(cardHabitat);
                    List<Card> cards = player.getGameBoard().getActiveHabitat();
                    int index = cards.indexOf(activationCard) - 1;
                    for(int i=index; i>=0; i--)
                    {
                        if (cards.get(i).getBirdInfo().getPowerColor() == PowerColor.BROWN)
                        {
                            activationCard = cards.get(i);
                            repaint();
                            return;
                        }
                    }
                    end = true;
                }
                if (end)
                {
                    endAbilityPanel();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!hasActivated)
        {
            if (c == 'n')
            {
                boolean end = false;
                if (activationCard.getBirdInfo().getPowerColor() == PowerColor.WHITE || activationCard.getBirdInfo().getPowerColor() == PowerColor.PINK)
                {
                    end = true;
                }
                else
                {
                    player.getGameBoard().setActiveHabitat(cardHabitat);
                    List<Card> cards = player.getGameBoard().getActiveHabitat();
                    int index = cards.indexOf(activationCard) - 1;
                    for(int i=index; i>=0; i--)
                    {
                        if (cards.get(i).getBirdInfo().getPowerColor() == PowerColor.BROWN)
                        {
                            activationCard = cards.get(i);
                            repaint();
                            return;
                        }
                    }
                    end = true;
                }
                if (end)
                {
                    endAbilityPanel();
                }
            }
            else if (c == 'y')
            {
                hasActivated = true;
                hasExecuted = false;
                if (abilityType.equals("BonusCardBehavior"))
                {
                    bonusCardsPos.put(GameState.cardManager.getRandomBonusCard(), new Pair(getWidth() / 2 - HAND_CARD_WIDTH, getHeight() - HAND_CARD_HEIGHT));
                    bonusCardsPos.put(GameState.cardManager.getRandomBonusCard(), new Pair(getWidth() / 2 + 25, getHeight() - HAND_CARD_HEIGHT));
                }
            }
        }
        else if (hasActivated && !hasExecuted)
        {
            if (abilityType.equals("FoodCacheBehavior"))
            {
                if (c == 'y')
                {
                    GameState.choseToCache = true;
                    actionWasSuccessful = activationCard.getBirdInfo().getBehavior().executePower();
                    hasExecuted = true;
                }
                else if (c == 'n')
                {
                    GameState.choseToCache = false;
                    actionWasSuccessful = activationCard.getBirdInfo().getBehavior().executePower();
                    hasExecuted = true;
                }
            }
            if (abilityType.equals("BonusCardBehavior") && hasSelectedBonus)
            {
                if (c == 'c')
                {
                    GameState.selectedBonusCard = chosenBonusCard;
                    activationCard.getBirdInfo().getBehavior().executePower();
                    hasExecuted = true;
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

    public void endAbilityPanel()
    {
        boolean roundEnd = false;
        GameState.activePlayer.decreaseActionsRemaining();
        int playerIndex = GameState.players.indexOf(GameState.activePlayer);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (hasActivated && !hasExecuted)
        {
            if (abilityType.equals("BonusCardBehavior"))
            {
                for(BonusCard c: bonusCardsPos.keySet())
                {
                    Pair p = bonusCardsPos.get(c);
                    if (x > p.getX() && x < p.getX() + HAND_CARD_WIDTH && y > p.getY() && y < p.getY() + HAND_CARD_HEIGHT)
                    {
                        hasSelectedBonus = true;
                        displayedCard = c.getImage();
                        chosenBonusCard = c;
                    }
                }
            }
            else if (abilityType.equals("RollDiceBehavior"))
            {
                // g.fillRect(1600, 300 + CARD_HEIGHT * 2 + 25, 250, 50);
                if (x > 1600 && x < 1850 && y > 300 + CARD_HEIGHT * 2 && y < 300 + CARD_HEIGHT * 2 + 75)
                {
                    
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
