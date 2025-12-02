package wingspan.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import wingspan.cards.Card;
import wingspan.cards.bonusCards.BonusCard;
import wingspan.cards.goals.Goal;
import wingspan.core.GameBoard;
import wingspan.core.GameState;
import wingspan.enums.Food;
import wingspan.enums.Habitat;
import wingspan.utils.Pair;

public class LayEggsPanel extends JPanel implements KeyListener, MouseListener {
    private GameBoard gameBoard;
    private BufferedImage plus;
    private Habitat selectedHabitat;
    private int selectedCardIndex = 0; // index of card within the habitat
    private HashMap<Card, Pair> cardPositions;
    private final int CARD_WIDTH = 125;
    private final int CARD_HEIGHT = 200;
    private BufferedImage boardImage;
    private BufferedImage background;
    private Map<Food, BufferedImage> foodToImage;
    private Map<Food, Integer> foodInventory;
    private int remainingChoices = 0;
    private boolean displayBonus = false;
    private final int HAND_CARD_HEIGHT = 180;
    private final int HAND_CARD_WIDTH = 120;
    private HashMap<Card, Pair> playerHandCardPositions;
    private HashMap<BonusCard, Pair> playerBonusCardPositions;
    private int TOKEN_WIDTH = 80;
    private int TOKEN_HEIGHT = 80;
    private Food selectedToken = null;
    private Food[] foods = {Food.BERRY, Food.WHEAT, Food.FISH, Food.RODENT, Food.INVERTEBRATE};
    private Pair[] tokenPositions;

    public LayEggsPanel() {
        gameBoard = GameState.activePlayer.getGameBoard();
        selectedHabitat = Habitat.GRASSLANDS;
        this.cardPositions = GameState.cardPositions;
        foodToImage = new HashMap<>();
        foodInventory = new HashMap<>();
        playerHandCardPositions = new HashMap<>();
        playerBonusCardPositions = new HashMap<>();

        try {
            plus = ImageIO.read(getClass().getResource("/Images/Plus.png"));
            boardImage = ImageIO.read(MainPanel.class.getResource("/Images/GameBoard.jpg"));
            background = ImageIO.read(DrawCardsPanel.class.getResource("/Images/BackgroundImage2.jpeg")); 
            foodToImage.put(Food.BERRY, ImageIO.read(getClass().getResourceAsStream("/Images/BerryToken.png")));
            foodToImage.put(Food.FISH, ImageIO.read(getClass().getResourceAsStream("/Images/FishToken.png")));
            foodToImage.put(Food.INVERTEBRATE, ImageIO.read(getClass().getResourceAsStream("/Images/InvertebrateToken.png")));
            foodToImage.put(Food.RODENT, ImageIO.read(getClass().getResourceAsStream("/Images/RodentToken.png")));
            foodToImage.put(Food.WHEAT, ImageIO.read(getClass().getResourceAsStream("/Images/WheatToken.png")));

            foodInventory = GameState.activePlayer.getFoodInventory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        try {
    
            super.paint(g);
            tokenPositions = new Pair[] {
                new Pair(getWidth() - 350, getHeight() - 800),
                new Pair(getWidth() - 250, getHeight() - 800),
                new Pair(getWidth() - 150, getHeight() - 800),
                new Pair(getWidth() - 300, getHeight() - 700),
                new Pair(getWidth() - 200, getHeight() - 700)
            };
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
            int playerIndex = GameState.players.indexOf(GameState.activePlayer);
            Graphics2D g2d = (Graphics2D)g;
            
            BufferedImage displayedCard = null;
            List<Card> forestCards = gameBoard.getForest();
            List<Card> grasslandsCards = gameBoard.getGrasslands();
            List<Card> wetlandsCards = gameBoard.getWetlands();

            //draw board
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
            
            //draw goals
            g.setColor(Color.WHITE);
            int goalXPos = getWidth() - 400;
            for(Goal goal: GameState.goalBoard.getGoals())
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



            //draw top right text

            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(1670, 0, 250, 75);
            g2d.setColor(Color.black);
            Font currentfont = g2d.getFont();
            Font newFont = currentfont.deriveFont(30F);
            g2d.setFont(newFont);
            g2d.drawString("Laying eggs", 1690, 50);


            //draw round counter
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

            //draw remaining choices
            g.setColor(Color.WHITE);
            g.drawString("Remaining Choices: " + remainingChoices, 50, getHeight() - 50);

            //draw hand
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

            //draw tokens

            for (int i = 0; i < foods.length; i++) {
                Food f = foods[i];
                Pair p = tokenPositions[i];
                g.drawImage(foodToImage.get(f), p.getX(), p.getY(), TOKEN_HEIGHT, TOKEN_WIDTH, null);
            }

            // draw text to exchange
            g.drawString("Choose a food token to", getWidth() - 400, getHeight() - 550);
            g.drawString("exchange for an extra egg (optional)", getWidth() - 400, getHeight() - 500);
            
            if (selectedToken != null) {
                Pair p = null;
                switch (selectedToken) {
                    
                    case BERRY: {
                        p = tokenPositions[0]; g.drawOval(p.getX(), p.getY(), TOKEN_WIDTH, TOKEN_HEIGHT);
                    } break;

                    case WHEAT: {
                        p = tokenPositions[0]; g.drawOval(p.getX(), p.getY(), TOKEN_WIDTH, TOKEN_HEIGHT);
                    } break;

                    case FISH: {
                        p = tokenPositions[0]; g.drawOval(p.getX(), p.getY(), TOKEN_WIDTH, TOKEN_HEIGHT);
                    } break;

                    case INVERTEBRATE: {
                        p = tokenPositions[0]; g.drawOval(p.getX(), p.getY(), TOKEN_WIDTH, TOKEN_HEIGHT);
                    } break;

                    case RODENT: {
                        p = tokenPositions[0]; g.drawOval(p.getX(), p.getY(), TOKEN_WIDTH, TOKEN_HEIGHT);
                    } break;
                }
            }

                // begin drawing the plus
            List<Card> cards = gameBoard.getCardsInHabitat(selectedHabitat);
            //System.out.println(cardPositions);
            for (int i = 0; i < cards.size(); i++) {                
                Card card = cards.get(i);
                Pair pair = cardPositions.get(card);
                
                if (card.getCurrentEggs() < card.getBirdInfo().getMaxEggs()) { // only draw (+) if more eggs can be added to bird
                    g.drawImage(plus, pair.getX(), pair.getY(), null);
                }
                g.drawString(card.getCurrentEggs() + " / " + card.getBirdInfo().getMaxEggs(), pair.getX(), pair.getY());
                
                if (i == selectedCardIndex) {
                    // highlight selected card
                    g.setColor(Color.RED);
                    g.drawRect(pair.getX() - 2, pair.getY() - 2, plus.getWidth() + 4, plus.getHeight() + 4);
                    g.setColor(Color.BLACK);
                }
                    
            }


        } catch (Exception ex) {
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'c') {

        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (selectedHabitat == Habitat.GRASSLANDS) selectedHabitat = Habitat.FOREST;
                else if (selectedHabitat == Habitat.WETLANDS) selectedHabitat = Habitat.GRASSLANDS;
                selectedCardIndex = 0;
                break;

            case KeyEvent.VK_DOWN:
                if (selectedHabitat == Habitat.FOREST) selectedHabitat = Habitat.GRASSLANDS;
                else if (selectedHabitat == Habitat.GRASSLANDS) selectedHabitat = Habitat.WETLANDS;
                selectedCardIndex = 0;
                break;

            case KeyEvent.VK_LEFT:
                if (selectedCardIndex > 0) selectedCardIndex--;
                break;

            case KeyEvent.VK_RIGHT:
                List<Card> cards = gameBoard.getCardsInHabitat(selectedHabitat);
                if (selectedCardIndex < cards.size() - 1) selectedCardIndex++;
                break;
        }
        //repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        int clickX = e.getX();
        int clickY = e.getY();

        for (int i = 0; i < foods.length; i++) {
            Food food = foods[i];
            Pair tokenPosition = tokenPositions[i];
            int tokenX = tokenPosition.getX();
            int tokenY = tokenPosition.getY();

            boolean withinX = clickX >= tokenX && clickX <= tokenX + TOKEN_WIDTH;
            boolean withinY = clickY >= tokenY && clickY <= tokenY + TOKEN_HEIGHT;
            boolean withinBounds = withinX && withinY;

            if (withinBounds) {
                selectedToken = food;
            }
            System.out.println(withinBounds);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    public void addNotify() {

        super.addNotify();
        requestFocus();
    }
}
