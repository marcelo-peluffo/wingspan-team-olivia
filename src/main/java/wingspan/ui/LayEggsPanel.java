package wingspan.ui;

import wingspan.cards.*;
import wingspan.enums.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import wingspan.core.*;

public class LayEggsPanel implements KeyListener {
    private GameBoard gameBoard;
    private BufferedImage plus;
    private Habitat selectedHabitat;
    private int selectedCardIndex = 0; // index of card within the habitat

    public LayEggsPanel() {
        gameBoard = GameState.activePlayer.getGameBoard();
        selectedHabitat = Habitat.GRASSLANDS;

        try {
            plus = ImageIO.read(getClass().getResource("/Images/Plus.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        int tempX = 0; // fix to actual coordinates later
        int tempY = 0;

        List<Card> cards = gameBoard.getCardsInHabitat(selectedHabitat);

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            g.drawImage(plus, tempX, tempY, null);
            g.drawString(card.getCurrentEggs() + " / " + card.getBirdInfo().getMaxEggs(), tempX, tempY);

            if (i == selectedCardIndex) {
                // highlight selected card
                g.setColor(Color.RED);
                g.drawRect(tempX - 2, tempY - 2, plus.getWidth() + 4, plus.getHeight() + 4);
                g.setColor(Color.BLACK);
            }

            tempX += plus.getWidth() + 10; // move to next card
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
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
