package wingspan.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import wingspan.cards.Card;
import wingspan.core.GameBoard;
import wingspan.core.GameState;
import wingspan.enums.Habitat;
import wingspan.utils.Pair;

public class LayEggsPanel extends JPanel implements KeyListener {
    private GameBoard gameBoard;
    private BufferedImage plus;
    private Habitat selectedHabitat;
    private int selectedCardIndex = 0; // index of card within the habitat
    private HashMap<Card, Pair> cardPositions;
    private final int CARD_WIDTH = 125;
    private final int CARD_HEIGHT = 200;

    public LayEggsPanel() {
        gameBoard = GameState.activePlayer.getGameBoard();
        selectedHabitat = Habitat.GRASSLANDS;
        this.cardPositions = GameState.cardPositions;
        try {
            plus = ImageIO.read(getClass().getResource("/Images/Plus.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        try {
            // int tempX = 0; // fix to actual coordinates later
            // int tempY = 0;
            super.paint(g);
            int playerIndex = GameState.players.indexOf(GameState.activePlayer);
            Graphics2D g2d = (Graphics2D)g;
            BufferedImage boardImage = ImageIO.read(MainPanel.class.getResource("/Images/GameBoard.jpg"));
            BufferedImage displayedCard = null;
            List<Card> forestCards = gameBoard.getForest();
            List<Card> grasslandsCards = gameBoard.getGrasslands();
            List<Card> wetlandsCards = gameBoard.getWetlands();


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
            
            
            // begin drawing the plus
            List<Card> cards = gameBoard.getCardsInHabitat(selectedHabitat);
            System.out.println(cardPositions);
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
                
                //tempX += plus.getWidth() + 10; // move to next card
            }
        } catch (IOException ex) {
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
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
}
