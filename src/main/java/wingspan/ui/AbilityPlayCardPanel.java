package wingspan.ui;
import wingspan.cards.goals.Goal;
import wingspan.core.*;
import wingspan.utils.Pair;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
//import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import wingspan.food.*;
import wingspan.cards.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import wingspan.cards.bonusCards.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import wingspan.enums.*;

public class AbilityPlayCardPanel extends JPanel implements MouseListener, KeyListener{

    private Goal[] goals;
    private Player activePlayer;
    private Map<Food, Integer> foodInventory;
    private Map<Food, BufferedImage> foodToImage;
    private BufferedImage boardImage;
    private BufferedImage background;
    private List<Card> forestCards;
    private List<Card> grasslandsCards;
    private List<Card> wetlandsCards;
    private BufferedImage displayedCard;
    private HashMap<Card, Pair> cardPositions; // this map will store all positions of the cards, and the card they're associated with
    private final int CARD_WIDTH = 125;
    private final int CARD_HEIGHT = 200;
    private int playerIndex;
    private String navigatorOption;
    private HashMap<Card, Pair> playerHandCardPositions;
    private HashMap<BonusCard, Pair> playerBonusCardPositions;
    private final int HAND_CARD_HEIGHT = 180;
    private final int HAND_CARD_WIDTH = 120;
    private final int TOKEN_SIZE = 61;
    private boolean displayBonus;
    private Card displayedCardInfo;
    private HashMap<Food, Integer> foodToExchange;
    private ArrayList<Food> foodList;
    private int numToExchange;
    private Food foodToGain;
    private int selectedFoodXPos;
    private int selectedFoodIndex;
    private Color selectColor;
    private HashMap<Food, Pair> foodTokenPos;
    private Card selectedCard;
    private boolean payAnyFood;
    private boolean choosingCard;
    private boolean placingBird;
    private boolean payingEggs;
    private boolean finished;
    private int tokenExchangeLimit;
    private Pair forestPos;
    private Pair grasslandsPos;
    private Pair wetlandsPos;
    private int requiredEggs;
    private int eggsPaid;
    private Card passIntoAbilityPanel;
    private Habitat habitatPlacedInto;
    private ArrayList<Food> foodChoices;
    private Habitat abilityHabitat;
    private ArrayList<Card> playerHand;

    public AbilityPlayCardPanel(Habitat h) throws IOException{
        goals = GameState.goalBoard.getGoals();
        activePlayer = GameState.activePlayer;
        foodInventory = activePlayer.getFoodInventory();
        foodToImage = new HashMap<>();
        background = ImageIO.read(getClass().getResourceAsStream("/Images/backgroundImage2.jpeg"));

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
        boardImage = ImageIO.read(MainPanel.class.getResource("/Images/GameBoard.jpg"));
        cardPositions = new HashMap<Card, Pair>();
        displayedCard = null;
        getPlayerCards(GameState.activePlayer);
        playerIndex = GameState.players.indexOf(GameState.activePlayer);
        navigatorOption = "GameBoard";
        playerHandCardPositions = new HashMap<>();
        displayBonus = false;
        playerBonusCardPositions = new HashMap<>();
        displayedCardInfo = null;
        selectedFoodXPos = 15;
        numToExchange = 0;
        foodToExchange = new HashMap<>();
        foodToExchange.put(Food.INVERTEBRATE, 0);
        foodToExchange.put(Food.WHEAT, 0);
        foodToExchange.put(Food.FISH, 0);
        foodToExchange.put(Food.RODENT, 0);
        foodToExchange.put(Food.BERRY, 0);
        foodToGain = null;
        foodList = new ArrayList<>();
        for(Food f: foodToImage.keySet())
        {
            foodList.add(f);
        }
        selectedFoodIndex = 0;
        selectColor = new Color(0, 170, 150);
        foodTokenPos = new HashMap<>();
        selectedCard = null;
        payAnyFood = false;
        choosingCard = true;
        placingBird = false;
        payingEggs = false;
        finished = false;
        tokenExchangeLimit = 2;
        eggsPaid = 0;
        foodChoices = new ArrayList<>();
        abilityHabitat = h;
        playerHand = getAllHabitatCards();
        
        initializeEmptyTilesPos();
    	addMouseListener(this);
        addKeyListener(this);
    }

    public ArrayList<Card> getAllHabitatCards()
    {
        ArrayList<Card> list = new ArrayList<>();
        for(Card c: GameState.activePlayer.getHand())
        {
            if (c.getBirdInfo().getHabitats().contains(abilityHabitat))
            {
                list.add(c);
            }
        }
        return list;
    }

    public void paint(Graphics g)
    {
		super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        //draw the food exchange interface
        if (choosingCard || payAnyFood)
        {
            g2d.setColor(new Color(232, 219, 97));
            g2d.fillRect(0, getHeight() - 80, 300, 100);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(10));
            g2d.drawRect(-5, getHeight() - 85, 305, 105);
            int foodXPos = 15;
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            for(Food f: foodToImage.keySet())
            {
                g2d.drawString("" + foodToExchange.get(f), foodXPos + 20, getHeight() - 100);
                g2d.drawImage(foodToImage.get(f), foodXPos, getHeight() - 67, 50, 50, null);
                foodXPos += 53;
            }
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(selectColor);
            g2d.drawRect(selectedFoodXPos, getHeight() - 67, 50, 50);
            
            if (foodToGain != null)
            {
                g2d.setColor(Color.BLUE);
                g2d.drawRect(foodTokenPos.get(foodToGain).getX(), foodTokenPos.get(foodToGain).getY(), TOKEN_SIZE, TOKEN_SIZE);
            }
            
            g2d.setColor(Color.GRAY);
            if ((foodToGain != null || payAnyFood) && numToExchange == tokenExchangeLimit)
                g2d.setColor(new Color(19,175,87));
            g2d.fillRect(50, getHeight() - 190, 200, 50);

            g2d.setFont(new Font("Arial", Font.BOLD, 15));
            g2d.setColor(Color.WHITE);
            String buttonText = "Confirm Exchange";
            if (payAnyFood)
                buttonText = "Confirm Payment";
            g2d.drawString(buttonText, 80, getHeight() - 160);

            g2d.setFont(new Font("Arial", Font.BOLD, 25));
            g2d.drawString("How to Exchange Food:", 40, getHeight() - 430);
            g2d.setFont(new Font("Arial", Font.PLAIN, 15));
            g2d.drawString("- Use left and right arrow keys to decide", 30, getHeight() - 410);
            g2d.drawString("which token to exchange", 30, getHeight() - 390);
            g2d.drawString("- Use up and down arrow keys to add or", 30, getHeight() - 370);
            g2d.drawString("remove a token to exchange", 30, getHeight() - 350);
            if (!payAnyFood)
            {
                g2d.drawString("- Click on one of the token images on", 30, getHeight() - 330);
                g2d.drawString("the interface above to choose", 30, getHeight() - 310);
                g2d.drawString("which token you want to trade for", 30, getHeight() - 290);
                g2d.drawString("- Once you have chosen " + tokenExchangeLimit + " tokens to trade", 30, getHeight() - 270);
                g2d.drawString("and the token you want to receive,", 30, getHeight() - 250);
                g2d.drawString("click '" + buttonText + "'", 30, getHeight() - 230);
            }
            else
            {
                g2d.drawString("- Once you have chosen " + tokenExchangeLimit + " tokens to trade", 30, getHeight() - 330);
                g2d.drawString("click '" + buttonText + "'", 30, getHeight() - 310);
            }
        }
        //draw the goals
        int goalXPos = getWidth() - 400;
        for(Goal goal: goals)
        {
            g.drawImage(goal.getImage(), goalXPos, getHeight() - 100, 100, 100, null);
            goalXPos += 100;
        }
        g.setColor(Color.WHITE);
        int arrowXPos = getWidth() - 450 + GameState.roundNum * 100;
        int[] xPoints = {arrowXPos - 25, arrowXPos, arrowXPos + 25};
        int[] yPoints = {getHeight() - 155, getHeight() - 110, getHeight() - 155};
        g.fillPolygon(xPoints, yPoints, 3);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Goals", getWidth() - 250, getHeight() - 160);

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
                foodTokenPos.put(food, new Pair(x, y));

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

        //draw the game board
        g.drawImage(boardImage, 350, 175, (int)(getWidth() * 0.6), (int)(getHeight() * 0.65), null);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(GameState.actionCubeColors.get(GameState.players.get(playerIndex)));
        String playerString = "Player " + (playerIndex + 1);
        g.drawString(playerString, getWidth() / 2 - 100, 50);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        String s = "";
        if (choosingCard)
            s = "Click on a card in your hand to play it, must have sufficient food, eggs, and space to do so";
        else if (payAnyFood)
            s = "Use the token exchange interface to choose which food tokens you want to pay";
        else if (placingBird)
            s = "Place the chosen bird card by clicking a valid tile";
        else if (payingEggs)
            s = "Click on any card on the board to pay eggs (" + eggsPaid + "/" + requiredEggs + ")";
        else if (finished)
            s = "Finished playing a card. Press 'c' to proceed";
        g.drawString(s, getWidth() / 2 - 200, 75);
        if (navigatorOption.equals("GameBoard"))
        {
            cardPositions.clear();
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
                g.setFont(new Font("Arial", Font.BOLD, 15));
                g.drawString("Eggs: " + displayedCardInfo.getCurrentEggs() + "/" + displayedCardInfo.getBirdInfo().getMaxEggs(), 1600, 720);
                if (choosingCard)
                {
                    g.drawString("Cached Food Tokens: " + displayedCardInfo.getFoodTokens().size(), 1600, 740);
                    g.drawString("Tucked Cards: " + displayedCardInfo.getTuckedCards().size(), 1600, 760);
                }
                if (payingEggs && displayedCardInfo.getCurrentEggs() > 0)
                {
                    g.drawString("Pay using this card? (press 'y' or 'n')", 1600, 740);
                }
                g.setColor(Color.BLACK);
            }
        }

        //draw the player's cards
        int leftEnd;
        int x1;
        if (!displayBonus)
        {
            if(playerHand.size() % 2 == 0){
            leftEnd = Math.max(getWidth()/2 - (5 + HAND_CARD_WIDTH) - ((playerHand.size()/2 - 1) * (HAND_CARD_WIDTH + 10)), 350);
            }
            else {
                leftEnd = Math.max(getWidth()/2 - HAND_CARD_WIDTH/2 - (((playerHand.size() + 1)/2 - 1) * (HAND_CARD_WIDTH + 10)), 350);
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
            for(Card c: playerHand){
                g.drawImage(c.getCardImage(), x1, 890, HAND_CARD_WIDTH, HAND_CARD_HEIGHT, null);
                playerHandCardPositions.put(c, new Pair(x1, 890));
                x1 += (10 + HAND_CARD_WIDTH) * ((40 - playerHand.size()) / (50.0 - (20 - playerHand.size())));
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

        //draw the instructions on the top right
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(1670, 0, 250, 75);
        g2d.setColor(Color.black);
        Font currentfont = g2d.getFont();
        Font newFont = currentfont.deriveFont(30F);
        g2d.setFont(newFont);
        g2d.drawString("Playing a Card", 1690, 50);

        //draw choose card button
        if (selectedCard != null && choosingCard)
        {
            g.setColor(Color.GRAY);
            System.out.println(canPlaceInHabitat(selectedCard));
            if (selectedCard.canPayFoodCost(GameState.activePlayer) && canPlaceInHabitat(selectedCard))
                g2d.setColor(new Color(19,175,87));
            g.fillRect(1600, 720, 250, 50);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.WHITE);
            g.drawString("Play this Card", 1660, 750);
        }

        //draw highlights where the player can place the card
        if (placingBird)
        {
            g2d.setStroke(new BasicStroke(10));
            g2d.setColor(new Color(19,175,87));
            if (abilityHabitat == Habitat.FOREST)
            {
                g2d.drawRect(forestPos.getX(), forestPos.getY(), CARD_WIDTH, CARD_HEIGHT);
            }
            if (abilityHabitat == Habitat.GRASSLANDS)
            {
                g2d.drawRect(grasslandsPos.getX(), grasslandsPos.getY(), CARD_WIDTH, CARD_HEIGHT);
            }
            if (abilityHabitat == Habitat.WETLANDS)
            {
                g2d.drawRect(wetlandsPos.getX(), wetlandsPos.getY(), CARD_WIDTH, CARD_HEIGHT);
            }
        }
    }

	@Override
	public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int mouseX = e.getX();
        int mouseY = e.getY();
        for(Card c: cardPositions.keySet())
        { 
            Pair position = cardPositions.get(c);
            if (mouseX > position.getX() && mouseY > position.getY() && mouseX < position.getX() + CARD_WIDTH && mouseY < position.getY() + CARD_HEIGHT && (choosingCard || payingEggs))
            {
                displayedCard = c.getCardImage();
                displayedCardInfo = c;
                selectedCard = null;
                break;
            }
        }
        if (!displayBonus)
        {
            for(Card c: playerHandCardPositions.keySet()){
            Pair p = playerHandCardPositions.get(c);
            if (playerHand.size() < 8)
            {
                if (x >= p.getX() && x <= p.getX() + HAND_CARD_WIDTH && y >= p.getY() && y <= p.getY() + HAND_CARD_HEIGHT && choosingCard)
                {
                    displayedCard = c.getCardImage();
                    displayedCardInfo = null;
                    selectedCard = c;
                    break;
                }
            }
            else
            {
                double spaceBetweenCards = (10 + HAND_CARD_WIDTH) * ((40 - playerHand.size()) / (50.0 - (20 - playerHand.size())));
                if (x >= p.getX() && x < p.getX() + spaceBetweenCards && y >= p.getY() && y < p.getY() + HAND_CARD_HEIGHT && choosingCard)
                {
                    displayedCard = c.getCardImage();
                    displayedCardInfo = null;
                    selectedCard = c;
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
                if (x >= p.getX() && x <= p.getX() + HAND_CARD_WIDTH && y >= p.getY() && y <= p.getY() + HAND_CARD_HEIGHT && (choosingCard || payingEggs))
                {
                    displayedCard = c.getImage();
                    displayedCardInfo = null;
                    selectedCard = null;
                    break;
                }
            }
            else
            {
                double spaceBetweenCards = (10 + HAND_CARD_WIDTH) * ((40 - GameState.activePlayer.getBonusCards().size()) / (50.0 - (20 - GameState.activePlayer.getBonusCards().size())));
                if (x >= p.getX() && x < p.getX() + spaceBetweenCards && y >= p.getY() && y < p.getY() + HAND_CARD_HEIGHT && (choosingCard || payingEggs))
                {
                    displayedCard = c.getImage();
                    displayedCardInfo = null;
                    selectedCard = null;
                    break;
                }
            }
        }
        }
        
        for(Food f: foodTokenPos.keySet())
        {
            Pair pos = foodTokenPos.get(f);
            if (x > pos.getX() && x < pos.getX() + TOKEN_SIZE && y > pos.getY() && y < pos.getY() + TOKEN_SIZE && choosingCard)
            {
                foodToGain = f;
            }
        }
        if ((foodToGain != null || payAnyFood) && numToExchange == tokenExchangeLimit)
        {
            if (x > 50 && x < 250 && y > getHeight() - 190 && y < getHeight() - 140)
            {
                if (foodToGain != null)
                {
                    for(Food f: foodToExchange.keySet())
                    {
                        GameState.activePlayer.removeFood(f, foodToExchange.get(f));
                        foodToExchange.put(f, 0);
                    }
                    GameState.activePlayer.addFood(foodToGain, 1);
                    numToExchange = 0;
                }
                else
                {
                    for(Food f: foodToExchange.keySet())
                    {
                        GameState.activePlayer.removeFood(f, foodToExchange.get(f));
                        foodToExchange.put(f, 0);
                    }
                    numToExchange = 0;
                    payAnyFood = false;
                    placingBird = true;
                }
            }
        }
        if (selectedCard != null && selectedCard.canPayFoodCost(GameState.activePlayer) && choosingCard && canPlaceInHabitat(selectedCard))
        {
            if (x > 1600 && x < 1850 && y > 720 && y < 770)
            {
                selectedCard.payFood(GameState.activePlayer);
                if (selectedCard.countAnyFood() > 0)
                {
                    tokenExchangeLimit = selectedCard.countAnyFood();
                    if (selectedCard.hasAnyFood())
                    {
                        for(Food f: foodToImage.keySet())
                        {
                            foodChoices.add(f);
                        }
                    }
                    else
                    {
                        for (Food f: selectedCard.getBirdInfo().getFoodCost()[0])
                        {
                            foodChoices.add(f);
                        }
                    }
                    foodToGain = null;
                    payAnyFood = true;
                    choosingCard = false;
                }
                else
                {
                    choosingCard = false;
                    placingBird = true;
                }
            }
        }
        if (placingBird)
        {
            if (abilityHabitat == Habitat.FOREST)
            {
                if (x > forestPos.getX() && x < forestPos.getX() + CARD_WIDTH && y > forestPos.getY() && y < forestPos.getY() + CARD_HEIGHT)
                {
                    System.out.println("Clicked forest");
                    GameState.activePlayer.getGameBoard().addCard(selectedCard, Habitat.FOREST);
                    GameState.activePlayer.removeCard(selectedCard);
                    passIntoAbilityPanel = selectedCard;
                    habitatPlacedInto = Habitat.FOREST;
                    selectedCard = null;
                    displayedCard = null;
                    requiredEggs = getNumEggsToPay(GameState.activePlayer.getGameBoard().getForest().size() - 1);
                    placingBird = false;
                    if (requiredEggs > 0)
                        payingEggs = true;
                    else
                        finished = true;
                    displayBonus = true;
                    repaint();
                    return;
                }
            }
            if (abilityHabitat == Habitat.GRASSLANDS)
            {
                if (x > grasslandsPos.getX() && x < grasslandsPos.getX() + CARD_WIDTH && y > grasslandsPos.getY() && y < grasslandsPos.getY() + CARD_HEIGHT)
                {
                    System.out.println("Clicked grasslands");
                    GameState.activePlayer.getGameBoard().addCard(selectedCard, Habitat.GRASSLANDS);
                    GameState.activePlayer.removeCard(selectedCard);
                    passIntoAbilityPanel = selectedCard;
                    selectedCard = null;
                    displayedCard = null;
                    habitatPlacedInto = Habitat.GRASSLANDS;
                    requiredEggs = getNumEggsToPay(GameState.activePlayer.getGameBoard().getGrasslands().size() - 1);
                    placingBird = false;
                    if (requiredEggs > 0)
                        payingEggs = true;
                    else
                        finished = true;
                    displayBonus = true;
                    repaint();
                    return;
                }
            }
            if (abilityHabitat == Habitat.WETLANDS)
            {
                
                if (x > wetlandsPos.getX() && x < wetlandsPos.getX() + CARD_WIDTH && y > wetlandsPos.getY() && y < wetlandsPos.getY() + CARD_HEIGHT)
                {
                    System.out.println("Clicked wetlands");
                    GameState.activePlayer.getGameBoard().addCard(selectedCard, Habitat.WETLANDS);
                    GameState.activePlayer.removeCard(selectedCard);
                    passIntoAbilityPanel = selectedCard;
                    selectedCard = null;
                    displayedCard = null;
                    habitatPlacedInto = Habitat.WETLANDS;
                    requiredEggs = getNumEggsToPay(GameState.activePlayer.getGameBoard().getWetlands().size() - 1);
                    placingBird = false;
                    if (requiredEggs > 0)
                        payingEggs = true;
                    else
                        finished = true;
                    displayBonus = true;
                    repaint();
                    return;
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
    public void getPlayerCards(Player p)
    {
        forestCards = p.getGameBoard().getForest();
        grasslandsCards = p.getGameBoard().getGrasslands();
        wetlandsCards = p.getGameBoard().getWetlands();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                if (selectedFoodXPos > 15) {selectedFoodXPos -= 53; selectedFoodIndex--; selectColor = new Color(0, 170, 150);} break;
            case KeyEvent.VK_RIGHT:
                if (selectedFoodXPos < 227) {selectedFoodXPos += 53; selectedFoodIndex++; selectColor = new Color(0, 170, 150);} break;
            case KeyEvent.VK_UP:
                if (GameState.activePlayer.getFoodInventory().get(foodList.get(selectedFoodIndex)) >= foodToExchange.get(foodList.get(selectedFoodIndex)) + 1 && numToExchange < tokenExchangeLimit)
                {
                    if (payAnyFood && !foodChoices.contains(foodList.get(selectedFoodIndex)))
                    {
                        selectColor = Color.RED;
                        break;
                    }
                    foodToExchange.put(foodList.get(selectedFoodIndex), foodToExchange.get(foodList.get(selectedFoodIndex)) + 1);
                    numToExchange++;
                    selectColor = new Color(0, 170, 150);
                }
                else
                {
                    selectColor = Color.RED;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (foodToExchange.get(foodList.get(selectedFoodIndex)) > 0)
                {
                    foodToExchange.put(foodList.get(selectedFoodIndex), foodToExchange.get(foodList.get(selectedFoodIndex)) - 1);
                    numToExchange--;
                    selectColor = new Color(0, 170, 150);
                }
                else
                {
                    selectColor = Color.RED;
                }
        }
        repaint();
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
        if (payingEggs)
        {
            if (displayedCardInfo != null)
            {
                if (c == 'y')
                {
                    if (displayedCardInfo.getCurrentEggs() > 0)
                    {
                        displayedCardInfo.removeEggs(1);
                        eggsPaid++;
                    }
                    
                    if (eggsPaid == requiredEggs)
                    {
                        payingEggs = false;
                        finished = true;
                    }
                }
                else
                {
                    displayedCardInfo = null;
                    displayedCard = null;
                }
            }
        }
        if (finished)
        {
            if (c == 'c')
            {
                destroyPanel();
            }
        }
        repaint();
    }

    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }

    public void initializeEmptyTilesPos()
    {
        forestPos = new Pair(610 + GameState.activePlayer.getGameBoard().getForest().size() * 175, 210);
        grasslandsPos = new Pair(610 + GameState.activePlayer.getGameBoard().getGrasslands().size() * 175, 425);
        wetlandsPos = new Pair(610 + GameState.activePlayer.getGameBoard().getWetlands().size() * 175, 640);
    }

    public boolean hasEnoughEggs(Habitat h, int currentEggs)
    {
        switch (GameState.activePlayer.getGameBoard().getCardsInHabitat(h).size())
        {
            case 0: return true;
            case 1: return currentEggs >= 1;
            case 2: return currentEggs >= 1;
            case 3: return currentEggs >= 2;
            case 4: return currentEggs >= 2;
            case 5: return false;
        }
        return false;
    }
    public int getNumEggsToPay(int numCards)
    {
        switch (numCards)
        {
            case 0: return 0;
            case 1: return 1;
            case 2: return 1;
            case 3: return 2;
            case 4: return 2;
        }
        return 99999;
    }

    public void destroyPanel()
    {
        if (passIntoAbilityPanel.getBirdInfo().getPowerColor() == PowerColor.WHITE)
        {
            setVisible(false);
            getParent().add(new AbilityPanel(GameState.activePlayer, passIntoAbilityPanel, habitatPlacedInto));
            getParent().repaint();
            getParent().remove(this);
            return;
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

    public boolean canPlaceInHabitat(Card c)
    {
        GameBoard gameBoard = GameState.activePlayer.getGameBoard();
        for(Habitat h: c.getBirdInfo().getHabitats())
        {
            if (gameBoard.getCardsInHabitat(h).size() < 5)
            {
                if (hasEnoughEggs(h, GameState.activePlayer.getTotalEggsAmount()))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
