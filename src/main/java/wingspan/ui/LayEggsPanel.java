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

    public LayEggsPanel() {
        gameBoard = GameState.activePlayer.getGameBoard();
        selectedHabitat = Habitat.GRASSLANDS;
        try {
            plus = ImageIO.read(getClass().getResource("/Images/Plus.jpg")); // plus image for clicking
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        int tempX = 0; // we need to figure out coordinates for each bird card
        int tempY = 0;

        for (Card card : gameBoard.returnAllCards()) { // show all options
            g.drawImage(plus, tempX, tempY, null);
            g.drawString(String.valueOf(card.getCurrentEggs()), tempX, tempY);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                boolean canGoUpOne = (selectedHabitat == Habitat.GRASSLANDS);
                boolean canGoUpTwo = (selectedHabitat == Habitat.WETLANDS);

                if (!canGoUpTwo || !canGoUpOne) {
                    break; // can't go up or down
                }

                if (canGoUpTwo) {
                    selectedHabitat = Habitat.GRASSLANDS;
                }

                if (canGoUpOne) {
                    selectedHabitat = Habitat.FOREST;
                }

                break;

            case KeyEvent.VK_DOWN:
                System.out.println("Down arrow");
                break;

            case KeyEvent.VK_LEFT:
                System.out.println("Left arrow");
                break;

            case KeyEvent.VK_RIGHT:
                System.out.println("Right arrow");
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
