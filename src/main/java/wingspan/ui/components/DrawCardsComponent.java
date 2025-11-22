package wingspan.ui.components;
import java.awt.*;
import javax.swing.*;
import wingspan.cards.*;
import wingspan.core.*;
import java.util.List;

public class DrawCardsComponent extends JPanel{

    List<Card> faceUpCards;

    public DrawCardsComponent()
    {
        faceUpCards = GameState.cardManager.getFaceUpCards();
    }

    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(getWidth() / 2 - 400, getHeight() / 2 - 350, getWidth() / 2 + 400, getHeight() / 2 - 350);
        g2d.drawLine(getWidth() / 2 - 400, getHeight() / 2 - 350, getWidth() / 2 - 400, getHeight() / 2 - 250);
        g2d.drawLine(getWidth() / 2, getHeight() / 2 - 350, getWidth() / 2, getHeight() / 2 - 250);
        g2d.drawLine(getWidth() / 2 + 400, getHeight() / 2 - 350, getWidth() / 2 + 400, getHeight() / 2 - 250);
        int xPos = getWidth() / 2 - 550;
        for(Card c: faceUpCards)
        {
            g.drawImage(c.getCardImage(), xPos, getHeight() / 2 - 250, 300, 450, null);
            xPos += 400;
        }
    }
}
