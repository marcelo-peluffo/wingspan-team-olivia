package wingspan.ui;

import java.awt.Color;
import java.awt.Graphics;
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

    public LayEggsPanel(HashMap<Card, Pair> cardPositions) {
        gameBoard = GameState.activePlayer.getGameBoard();
        selectedHabitat = Habitat.GRASSLANDS;
        this.cardPositions = cardPositions;
        try {
            plus = ImageIO.read(getClass().getResource("/Images/Plus.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        // int tempX = 0; // fix to actual coordinates later
        // int tempY = 0;

        List<Card> cards = gameBoard.getCardsInHabitat(selectedHabitat);

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
