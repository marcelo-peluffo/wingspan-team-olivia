package wingspan.ui.components;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.List;
import java.util.HashMap;
import wingspan.core.*;
import wingspan.cards.*;
import wingspan.utils.Pair;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameBoardComponent extends JPanel implements MouseListener{
    
    private BufferedImage boardImage;
    private List<Card> forestCards;
    private List<Card> grasslandsCards;
    private List<Card> wetlandsCards;
    private BufferedImage displayedCard;
    private HashMap<Card, Pair> cardPositions; // this map will store all positions of the cards, and the card they're associated with
    private final int CARD_WIDTH = 125;
    private final int CARD_HEIGHT = 200;
    private int playerIndex;

    public GameBoardComponent() throws IOException
    {
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
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(7));
        g2d.drawRect(getWidth() / 2 - 255, 120, 409, 50);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(getWidth() / 2 - 251, 124, 402, 51);
        g2d.setColor(Color.BLACK);
        g2d.drawLine(getWidth() / 2 - 200, 127, getWidth() / 2 - 200, 172);
        g2d.drawLine(getWidth() / 2 + 100, 127, getWidth() / 2 + 100, 172);
        int[] xPoints = {getWidth() / 2 - 240, getWidth() / 2 - 220, getWidth() / 2 - 220};
        int[] yPoints = {150, 135, 165};
        g2d.fillPolygon(xPoints, yPoints, 3);
        int[] xPoints2 = {getWidth() / 2 + 140, getWidth() / 2 + 120, getWidth() / 2 + 120};
        int[] yPoints2 = {150, 135, 165};
        g2d.fillPolygon(xPoints2, yPoints2, 3);
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

    public void getPlayerCards(Player p)
    {
        forestCards = p.getGameBoard().getForest();
        grasslandsCards = p.getGameBoard().getGrasslands();
        wetlandsCards = p.getGameBoard().getWetlands();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
