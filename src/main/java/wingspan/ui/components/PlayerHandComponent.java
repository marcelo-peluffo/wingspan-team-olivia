package wingspan.ui.components;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import wingspan.core.*;
import wingspan.cards.*;
import wingspan.utils.Pair;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerHandComponent extends JPanel implements MouseListener{

    private BufferedImage displayedCard;
    private HashMap<Card, Pair> cardPositions;
    private final int CARD_HEIGHT = 180;
    private final int CARD_WIDTH = 120;

    public PlayerHandComponent(){
        cardPositions = new HashMap<>();
        displayedCard = null;
        addMouseListener(this);
    }

    public void paint(Graphics g){
        super.paint(g);

        int leftEnd;
        int x;
        if(GameState.activePlayer.getHand().size() % 2 == 0){
            leftEnd = Math.max(getWidth()/2 - (5 + CARD_WIDTH) - ((GameState.activePlayer.getHand().size()/2 - 1) * (CARD_WIDTH + 10)), 350);
        }
        else {
            leftEnd = Math.max(getWidth()/2 - CARD_WIDTH/2 - (((GameState.activePlayer.getHand().size() + 1)/2 - 1) * (CARD_WIDTH + 10)), 350);
        }

        x = leftEnd;
        for(Card c: GameState.activePlayer.getHand()){
            g.drawImage(c.getCardImage(), x, 890, CARD_WIDTH, CARD_HEIGHT, null);
            cardPositions.put(c, new Pair(x, 890));
            x += (10 + CARD_WIDTH) * ((40 - GameState.activePlayer.getHand().size()) / (50.0 - (20 - GameState.activePlayer.getHand().size())));
        }
        g.drawImage(displayedCard, 1600, 300, 250, 400, null);
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for(Card c: cardPositions.keySet()){
            Pair p = cardPositions.get(c);
            if (GameState.activePlayer.getHand().size() < 8)
            {
                if (x >= p.getX() && x <= p.getX() + CARD_WIDTH && y >= p.getY() && y <= p.getY() + CARD_HEIGHT)
                {
                    displayedCard = c.getCardImage();
                    break;
                }
            }
            else
            {
                double spaceBetweenCards = (10 + CARD_WIDTH) * ((40 - GameState.activePlayer.getHand().size()) / (50.0 - (20 - GameState.activePlayer.getHand().size())));
                if (x >= p.getX() && x < p.getX() + spaceBetweenCards && y >= p.getY() && y < p.getY() + CARD_HEIGHT)
                {
                    displayedCard = c.getCardImage();
                    break;
                }
            }
        }
        repaint();
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
