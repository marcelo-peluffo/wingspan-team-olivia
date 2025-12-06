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
import wingspan.enums.*;
import wingspan.food.*;
import wingspan.core.*;

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
    private HashMap<Habitat, Pair> availableTilePos;
    private HashMap<Card, Pair> faceUpCardPos;
    private Habitat[] habitats = {Habitat.FOREST, Habitat.GRASSLANDS, Habitat.WETLANDS};
    private boolean cannotSwitch;
    private Card chosenCard;
    private boolean drewOnce;
    private boolean onSecondBehavior;

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
        GameState.activeCardHabitat = cardHabitat;
        cardPositions = new HashMap<Card, Pair>();
        displayedCard = null;
        player = p;
        getPlayerCards(player);
        foodInventory = p.getFoodInventory();
        foodToImage = new HashMap<>();
        this.activationCard = activationCard;
        hasActivated = false;
        this.cardHabitat = cardHabitat;
        goals = GameState.goalBoard.getGoals();
        bonusCardsPos = new HashMap<>();
        hasSelectedBonus = false;
        availableTilePos = new HashMap<>();
        faceUpCardPos = new HashMap<>();
        cannotSwitch = false;
        chosenCard = null;
        drewOnce = false;
        onSecondBehavior = false;

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
        g.setColor(Color.WHITE);
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
                xPos += 50;
            }
        }
        else if (abilityType.equals("WingspanBehavior"))
        {
            GameState.wingspanCard = GameState.cardManager.getRandomCard();
            displayedCard = GameState.wingspanCard.getCardImage();
            actionWasSuccessful = activationCard.getBirdInfo().getBehavior().executePower();
            hasExecuted = true;
            repaint();
        }
        else if (abilityType.equals("MoveCardBehavior"))
        {
            GameBoard playerGameBoard = player.getGameBoard();
            for(Habitat h: habitats)
            {
                if (playerGameBoard.getCardsInHabitat(h).size() < 5 && h != cardHabitat)
                {
                    int xPos = 610 + GameState.activePlayer.getGameBoard().getCardsInHabitat(h).size() * 175;
                    int yPos = 0;
                    switch (h)
                    {
                        case FOREST: yPos = 210; break;
                        case GRASSLANDS: yPos = 425; break;
                        case WETLANDS: yPos = 640; break;
                    }
                    Graphics2D g2d = (Graphics2D)g;
                    g2d.setStroke(new BasicStroke(10));
                    g2d.setColor(new Color(19,175,87));
                    g.drawRect(xPos, yPos, CARD_WIDTH, CARD_HEIGHT);
                    availableTilePos.put(h, new Pair(xPos, yPos));
                }
            }
        }
        else if (abilityType.equals("DrawCardBehavior"))
        {
            faceUpCardPos.clear();
            int xPos = getWidth() / 2 - HAND_CARD_WIDTH - 50;
            for(int i=0; i<GameState.cardManager.getFaceUpCards().size(); i++)
            {
                faceUpCardPos.put(GameState.cardManager.getFaceUpCards().get(i), new Pair(xPos, getHeight() - HAND_CARD_HEIGHT));
                xPos += HAND_CARD_WIDTH + 25;
            }
            g.setFont(new Font("Arial", Font.BOLD, 15));
            for(Card c: faceUpCardPos.keySet())
            {
                Pair pos = faceUpCardPos.get(c);
                g.drawImage(c.getCardImage(), pos.getX(), pos.getY(), HAND_CARD_WIDTH, HAND_CARD_HEIGHT, null);
            }
            String s = "Click one of the 3 face up cards";
            if (chosenCard != null)
                s = "Press 'c' to confirm";
            g.drawString(s, 1600, 300 + CARD_HEIGHT * 2 + 25);
            if (chosenCard == null)
            {
                g.drawString("Or press 'r' to draw a random card", 1600, 300 + CARD_HEIGHT * 2 + 45);
            }
        }
        else if (abilityType.equals("DiscardCardsBehavior") || abilityType.equals("TuckCardBehavior") || abilityType.equals("LayEggAnyBehavior"))
        {
            g.setFont(new Font("Arial", Font.BOLD, 15));
            String s = "Click on one of your cards to ";
            if (abilityType.equals("TuckCardBehavior"))
                s += "tuck";
            else if (abilityType.equals("DiscardCardsBehavior"))
                s += "discard";
            else
                s += "place an egg";
            if (chosenCard != null) 
            {
                s = "Press 'c' to confirm";
            }
            g.drawString(s, 1575, 300 + CARD_HEIGHT * 2 + 25);
            if (abilityType.equals("LayEggAnyBehavior") && chosenCard != null)
            {
                g.drawString("Eggs: " + chosenCard.getCurrentEggs() + "/" + chosenCard.getBirdInfo().getMaxEggs(), 1575, 300 + CARD_HEIGHT * 2 + 45);
            }
        }
        else if (abilityType.equals("GainFoodAllBehavior") || abilityType.equals("GainFoodBehavior") || abilityType.equals("CacheBehavior") || abilityType.equals("LayEggBehavior") || abilityType.equals("DrawCardsAllBehavior"))
        {
            if (!onSecondBehavior)
                actionWasSuccessful = activationCard.getBirdInfo().getBehavior().executePower();
            else
                actionWasSuccessful = activationCard.getBirdInfo().getBehavior().getSecondBehavior().executePower();
            hasExecuted = true;
            repaint();
        }
        else if (abilityType.equals("DiscardFoodBehavior"))
        {
            if (!activationCard.getBirdInfo().getBehavior().executePower())
            {
                hasExecuted = true;
            }
            else
            {
                for(int i=0; i<2; i++)
                {
                    activationCard.tuckCard(GameState.cardManager.getRandomCard());
                }
                hasExecuted = true;
            }
            repaint();
        }
        else if (abilityType.equals("PlayCardBehavior"))
        {
            setVisible(false);
            try
            {
                getParent().add(new AbilityPlayCardPanel(cardHabitat));
            }
            catch (Exception ex)
            {
                System.out.println("Failed to load play card panel");
            }
            getParent().repaint();
            getParent().remove(this);
        }
    }

    public void drawAbilityEndUI(Graphics g)
    {
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.setColor(Color.WHITE);
        if (abilityType.equals("FoodCacheBehavior"))
        {
            String s = "";
            if (!actionWasSuccessful)
                s = "Bird feeder didn't have any wheat";
            else if (actionWasSuccessful && GameState.choseToCache)
                s = "Successfully cached 1 wheat token";
            else
                s = "Gained 1 wheat token";
            g.drawString(s, 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("GainFoodAllBehavior"))
        {
            g.drawString("All players gained 1 food", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("GainFoodBehavior"))
        {
            g.drawString("You gained 1 food token", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("CacheBehavior"))
        {
            g.drawString("Successfully cached 1 wheat token", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("BonusCardBehavior"))
        {
            g.drawString("Added Bonus Card to hand", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("RollDiceBehavior"))
        {
            int xPos = 1600;
            for(FoodDice fd: GameState.foodManager.getUsedDice())
            {
                g.drawImage(fd.getImage(), xPos, 300 + CARD_HEIGHT * 2 + 100, 50, 50, null);
                xPos += 50;
            }
            if (actionWasSuccessful)
                g.drawString("Cached 1 food token on this card", 1600, 300 + CARD_HEIGHT * 2 + 25);
            else
                g.drawString("Aw man :(", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("WingspanBehavior"))
        {
            if (actionWasSuccessful)
                g.drawString("Successfully tucked this card", 1600, 300 + CARD_HEIGHT * 2 + 25);
            else
                g.drawString("Unfortunate", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("MoveCardBehavior"))
        {
            if (actionWasSuccessful)
                g.drawString("Successfully moved this card", 1600, 300 + CARD_HEIGHT * 2 + 25);
            else
                g.drawString("This card is unable to be moved", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("LayEggBehavior"))
        {
            if (actionWasSuccessful)
                g.drawString("Added 1 egg to this card", 1600, 300 + CARD_HEIGHT * 2 + 25);
            else
                g.drawString("Card was at max egg capacity", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("DrawCardsAllBehavior"))
        {
            g.drawString("All players received 1 card", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("DiscardCardsBehavior"))
        {
            g.drawString("Successfully drew + discarded", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("DrawCardBehavior"))
        {
            g.drawString("Successfully drew a card", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("TuckCardBehavior"))
        {
            g.drawString("You don't have any cards to tuck", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("DiscardFoodBehavior"))
        {
            if (actionWasSuccessful)
                g.drawString("Successfully tucked 2 cards", 1600, 300 + CARD_HEIGHT * 2 + 25);
            else
                g.drawString("You don't have the required food", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("PlayCardBehavior"))
        {
            g.drawString("You don't have enough resources to play a card", 1550, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("EmptyBehavior"))
        {
            g.drawString("This card has no behavior", 1600, 300 + CARD_HEIGHT * 2 + 25);
        }
        else if (abilityType.equals("LayEggAnyBehavior"))
        {
            if (actionWasSuccessful)
                g.drawString("Successfully placed 1 egg", 1600, 300 + CARD_HEIGHT * 2 + 25);
            else
                g.drawString("Cannot execute this ability right now", 1575, 300 + CARD_HEIGHT * 2 + 25);
        }
        g.drawString("Press ENTER to proceed", 1600, 300 + CARD_HEIGHT * 2 + 50);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        System.out.println(abilityType);
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
        if (!(hasActivated && (abilityType.equals("BonusCardBehavior") || abilityType.equals("DrawCardBehavior"))))
        {
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
        else if (hasActivated && hasExecuted && abilityType.equals("WingspanBehavior"))
        {
            g.drawImage(displayedCard, 1600, 300, CARD_WIDTH * 2, CARD_HEIGHT * 2, null);
        }
        else if (hasActivated && !hasExecuted && (abilityType.equals("DrawCardBehavior") || abilityType.equals("DiscardCardsBehavior") || abilityType.equals("TuckCardBehavior") || abilityType.equals("LayEggAnyBehavior")) && chosenCard != null)
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
                    if (abilityType.equals("MoveCardBehavior"))
                    {
                        index = cards.size() - 1;
                    }
                    for(int i=index; i>=0; i--)
                    {
                        if (cards.get(i).getBirdInfo().getPowerColor() == PowerColor.BROWN)
                        {
                            activationCard = cards.get(i);
                            abilityType = activationCard.getBirdInfo().getBehavior().describe();
                            hasExecuted = false;
                            hasActivated = false;
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
                if (abilityType.equals("MoveCardBehavior"))
                {
                    boolean hasSpace = false;
                    boolean isOnRight = false;
                    List<Card> habitat = player.getGameBoard().getCardsInHabitat(cardHabitat);
                    if (habitat.indexOf(activationCard) == habitat.size() - 1)
                    {
                        isOnRight = true;
                    }
                    for(Habitat h: habitats)
                    {
                        if (cardHabitat != h && player.getGameBoard().getCardsInHabitat(h).size() < 5)
                            hasSpace = true;
                    }
                    if (!isOnRight || !hasSpace)
                    {
                        hasExecuted = true;
                        actionWasSuccessful = false;
                    }
                }
                if (abilityType.equals("TuckCardBehavior"))
                {
                    if (player.getHand().isEmpty())
                    {
                        hasExecuted = true;
                        actionWasSuccessful = false;
                    }
                }
                if (abilityType.equals("PlayCardBehavior"))
                {
                    boolean hasSpace = false;
                    boolean canPlayCard = false;
                    boolean canPayEggs = false;
                    if (GameState.activePlayer.getGameBoard().getCardsInHabitat(cardHabitat).size() < 5)
                    {
                        hasSpace = true;
                    }
                    for(Card card: player.getHand())
                    {
                        if (card.getBirdInfo().getHabitats().contains(cardHabitat) && card.couldPayFoodCost(player))
                        {
                            canPlayCard = true;
                            break;
                        }
                    }
                    int eggs = player.getTotalEggsAmount();
                    if (eggs >= 2)
                    {
                        canPayEggs = true;
                    }
                    else if (eggs == 1 && player.getGameBoard().getCardsInHabitat(cardHabitat).size() <= 2)
                    {
                        canPayEggs = true;
                    }
                    if (!hasSpace || !canPlayCard || !canPayEggs)
                    {
                        hasExecuted = true;
                    }
                }
                if (abilityType.equals("EmptyBehavior"))
                {
                    hasExecuted = true;
                }
                if (abilityType.equals("LayEggAnyBehavior"))
                {
                    boolean canPlaceEgg = false;
                    NestType validNest = activationCard.getBirdInfo().getBehavior().getBehaviorParams().nestType;
                    for(Card card: player.getGameBoard().returnAllCards())
                    {
                        if ((card.getBirdInfo().getNestType() == validNest || validNest == NestType.STAR) && !card.isAtMaxEggs())
                        {
                            if (!(card.equals(activationCard) && activationCard.getBirdInfo().getPowerColor() == PowerColor.PINK))
                            {
                                canPlaceEgg = true;
                            }
                        }
                    }
                    if (activationCard.getCurrentEggs() == 0)
                        canPlaceEgg = false;
                    if (!canPlaceEgg)
                    {
                        hasExecuted = true;
                        actionWasSuccessful = false;
                    }
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
                    for(BonusCard card: bonusCardsPos.keySet())
                    {
                        if (!card.equals(GameState.selectedBonusCard))
                        {
                            CardManager.bonusCards.add(card);
                        }
                    }
                    activationCard.getBirdInfo().getBehavior().executePower();
                    hasExecuted = true;
                }
            }
            if (abilityType.equals("DrawCardBehavior"))
            {
                if (c == 'r')
                {
                    chosenCard = GameState.cardManager.getRandomCard();
                    displayedCard = chosenCard.getCardImage();
                    cannotSwitch = true;
                }
                if (chosenCard != null && c == 'c')
                {
                    if (GameState.cardManager.getFaceUpCards().contains(chosenCard))
                    {
                        GameState.cardManager.getVisibleCard(GameState.cardManager.getFaceUpCards().indexOf(chosenCard));
                    }
                    GameState.chosenCards.add(chosenCard);
                    ArrayList<String> draw2 = new ArrayList<>();
                    draw2.add("Common Yellowthroat");
                    draw2.add("Pied-Billed Grebe");
                    draw2.add("Red-Breasted Merganser");
                    draw2.add("Ruddy Duck");
                    draw2.add("Wood Duck");
                    if (draw2.contains(activationCard.getBirdInfo().getName()) && !drewOnce)
                    {
                        drewOnce = true;
                        chosenCard = null;
                        cannotSwitch = false;
                    }
                    else
                    {
                        GameState.cardManager.refillVisibleCards();
                        if (onSecondBehavior)
                            activationCard.getBirdInfo().getBehavior().getSecondBehavior().executePower();
                        else
                            activationCard.getBirdInfo().getBehavior().executePower();
                        if (activationCard.getBirdInfo().getBehavior().getSecondBehavior() == null || onSecondBehavior)
                        {
                            hasExecuted = true;
                        }
                        else
                        {
                            abilityType = "DiscardCardsBehavior";
                            chosenCard = null;
                            cannotSwitch = false;
                        }
                    }
                    
                }
            }
            if (abilityType.equals("DiscardCardsBehavior"))
            {
                if (chosenCard != null && c == 'c')
                {
                    player.getHand().remove(chosenCard);
                    hasExecuted = true;
                }
            }
            if (abilityType.equals("TuckCardBehavior"))
            {
                if (chosenCard != null && c == 'c')
                {
                    player.getHand().remove(chosenCard);
                    activationCard.tuckCard(chosenCard);
                    if (!onSecondBehavior)
                    {
                        abilityType = activationCard.getBirdInfo().getBehavior().getSecondBehavior().describe();
                        onSecondBehavior = true;
                        chosenCard = null;
                    }
                }
            }
            if (abilityType.equals("LayEggAnyBehavior"))
            {
                if (chosenCard != null && c == 'c')
                {
                    chosenCard.addEggs(1);
                    activationCard.triggerPower();
                    actionWasSuccessful = true;
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
        if (cardHabitat == Habitat.GRASSLANDS && activationCard.getBirdInfo().getPowerColor() == PowerColor.BROWN)
        {
            for(Player p: GameState.players)
            {
                if (!p.equals(player))
                {
                    for(Card card: p.getGameBoard().returnAllCards())
                    {
                        if (card.getBirdInfo().getPowerColor() == PowerColor.PINK && card.getBirdInfo().getBehavior().describe().equals("LayEggAnyBehavior") && !card.hasActivatedPower())
                        {
                            Habitat h;
                            if (p.getGameBoard().getForest().indexOf(card) > -1)
                                h = Habitat.FOREST;
                            else if (p.getGameBoard().getGrasslands().indexOf(card) > -1)
                                h = Habitat.GRASSLANDS;
                            else
                                h = Habitat.WETLANDS;
                            setVisible(false);
                            getParent().add(new AbilityPanel(p, card, h));
                            getParent().repaint();
                            getParent().remove(this);
                            return;
                        }
                    }
                }
            }
        }
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
                    actionWasSuccessful = activationCard.getBirdInfo().getBehavior().executePower();
                    hasExecuted = true;
                }
            }
            else if (abilityType.equals("MoveCardBehavior"))
            {
                for(Habitat h: availableTilePos.keySet())
                {
                    Pair p = availableTilePos.get(h);
                    if (x > p.getX() && x < p.getX() + CARD_WIDTH && y > p.getY() && y < p.getY() + CARD_HEIGHT)
                    {
                        GameState.chosenHabitat = h;
                        actionWasSuccessful = activationCard.getBirdInfo().getBehavior().executePower();
                        hasExecuted = true;
                        break;
                    }
                }
            }
            else if (abilityType.equals("DrawCardBehavior"))
            {
                for(Card c: faceUpCardPos.keySet())
                {
                    Pair p = faceUpCardPos.get(c);
                    if (x > p.getX() && x < p.getX() + HAND_CARD_WIDTH && y > p.getY() && y < p.getY() + HAND_CARD_HEIGHT && !cannotSwitch)
                    {
                        displayedCard = c.getCardImage();
                        chosenCard = c;
                    }
                }
            }
            else if (abilityType.equals("DiscardCardsBehavior") || abilityType.equals("TuckCardBehavior"))
            {
                for(Card c: cardPositions.keySet())
                {
                    Pair p = cardPositions.get(c);
                    if (x > p.getX() && x < p.getX() + HAND_CARD_WIDTH && y > p.getY() && y < p.getY() + HAND_CARD_HEIGHT && player.getHand().contains(c))
                    {
                        displayedCard = c.getCardImage();
                        chosenCard = c;
                    }
                }
            }
            else if (abilityType.equals("LayEggAnyBehavior"))
            {
                for(Card c: cardPositions.keySet())
                {
                    Pair p = cardPositions.get(c);
                    if (x > p.getX() && x < p.getX() + HAND_CARD_WIDTH && y > p.getY() && y < p.getY() + HAND_CARD_HEIGHT && player.getGameBoard().returnAllCards().contains(c) && (c.getBirdInfo().getNestType() == activationCard.getBirdInfo().getBehavior().getBehaviorParams().nestType || activationCard.getBirdInfo().getBehavior().getBehaviorParams().nestType == NestType.STAR) && !c.isAtMaxEggs())
                    {
                        if (!(c.equals(activationCard) && activationCard.getBirdInfo().getPowerColor() == PowerColor.PINK))
                        {
                            displayedCard = c.getCardImage();
                            chosenCard = c;
                        }
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
