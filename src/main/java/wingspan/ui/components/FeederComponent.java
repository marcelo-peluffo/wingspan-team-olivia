package wingspan.ui.components;
import wingspan.food.*;
import wingspan.enums.Food;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class FeederComponent extends JPanel implements MouseListener {

    // Backend
    private FoodManager foodManager;
    private int remainingChoices;

    // Bird Feeder
    private BufferedImage feederImage;

	// Dice
    private BufferedImage diceMulti;
    private BufferedImage diceInv;
    private BufferedImage diceWheat;
    private BufferedImage diceRodent;
    private BufferedImage diceBerry;
    private BufferedImage diceFish;

    // Multi-choice for diceMulti
    private BufferedImage tokenInv;
    private BufferedImage tokenWheat;

    // Buttons & UI rects
    private Rectangle rerollButtonRect;
    private Rectangle multiInvRect;
    private Rectangle multiWheatRect;

    // State
    private boolean choosingMulti = false;
    private FoodDice multiDice;

    public FeederComponent(FoodManager manager, int choicesAllowed) {
        this.foodManager = manager;
        this.remainingChoices = choicesAllowed;

        try {
            feederImage = ImageIO.read(FeederComponent.class.getResource("/Images/BirdFeederImage.png"));

			diceMulti = ImageIO.read(FeederComponent.class.getResource("/Images/MultiDice.jpg"));
            diceInv = ImageIO.read(FeederComponent.class.getResource("/Images/InvertebrateDice.jpg"));
            diceWheat = ImageIO.read(FeederComponent.class.getResource("/Images/WheatDice.jpg"));
            diceRodent = ImageIO.read(FeederComponent.class.getResource("/Images/RodentDice.jpg"));
            diceBerry = ImageIO.read(FeederComponent.class.getResource("/Images/BerryDice.jpg"));
            diceFish = ImageIO.read(FeederComponent.class.getResource("/Images/FishDice.jpg"));

            tokenInv   = ImageIO.read(getClass().getResource("/Images/InvertebrateToken.png"));
            tokenWheat = ImageIO.read(getClass().getResource("/Images/WheatToken.png"));

        } catch (Exception e) {
            System.out.println("Exception error");
    		return;
        }

        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        // Left Bird Feeder Image
        g.drawImage(feederImage, 60, 180, 260, 310, null);

        // Remaining Choices Text
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        g.drawString("Remaining choices: " + remainingChoices, 460, 130);

        // Middle Dice Area Box
        int boxX = 380, boxY = 170, boxW = 600, boxH = 250;
        g2.setStroke(new BasicStroke(5));
        g.setColor(Color.BLACK);
        g.drawRect(boxX, boxY, boxW, boxH);

        // Draw Dice from FoodManager
        ArrayList<FoodDice> diceList = foodManager.getBirdFeeder();
        for (int i = 0; i < diceList.size(); i++) {
            FoodDice die = diceList.get(i);
            BufferedImage face = getDiceImage(die);
            int dx = boxX + 30 + i * 110;
            int dy = boxY + 30;
            g.drawImage(face, dx, dy, 100, 100, null);
        }

        // Reroll Button
        boolean canReroll = canRerollNow();
        rerollButtonRect = new Rectangle(550, 450, 180, 60);
        g.setColor(canReroll ? new Color(250, 230, 90) : new Color(170, 170, 170));
        g.fillRect(rerollButtonRect.x, rerollButtonRect.y, rerollButtonRect.width, rerollButtonRect.height);
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        g.drawString("REROLL", rerollButtonRect.x + 40, rerollButtonRect.y + 40);

        // Multi-choice
        if (choosingMulti) {
            drawMultiChoice(g2);
        }
    }

    // Draw bottom-left multi-choice box
    private void drawMultiChoice(Graphics2D g) {
        int bx = 120, by = 520, bw = 240, bh = 130;

        g.setStroke(new BasicStroke(5));
        g.setColor(Color.BLACK);
        g.drawRect(bx, by, bw, bh);

        multiInvRect = new Rectangle(bx + 20, by + 25, 80, 80);
        multiWheatRect = new Rectangle(bx + 140, by + 25, 80, 80);

        g.drawImage(tokenInv, multiInvRect.x, multiInvRect.y, 80, 80, null);
        g.drawImage(tokenWheat, multiWheatRect.x, multiWheatRect.y, 80, 80, null);
    }

    // Decide which image to use for each die
    private BufferedImage getDiceImage(FoodDice die) {
        switch (die.getFood()) {
            case ANY:          return diceMulti;
            case INVERTEBRATE: return diceInv;
            case WHEAT:        return diceWheat;
            case RODENT:       return diceRodent;
            case BERRY:        return diceBerry;
            case FISH:         return diceFish;
        }
        return diceMulti;
    }

    // Check if reroll allowed
    private boolean canRerollNow() {
        ArrayList<FoodDice> dice = foodManager.getBirdFeeder();
        if (dice.isEmpty()) return false;

        Food first = dice.get(0).getFood();
        for (FoodDice d : dice) {
            if (d.getFood() != first)
                return false;
        }
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX(), y = e.getY();

        // Multi-choice clicking
        if (choosingMulti) {
            if (multiInvRect.contains(x,y)) {
                finishMultiChoice(Food.INVERTEBRATE);
                return;
            }
            if (multiWheatRect.contains(x,y)) {
                finishMultiChoice(Food.WHEAT);
                return;
            }
        }

        // Reroll
        if (rerollButtonRect.contains(x,y) && canRerollNow()) {
            try {
                foodManager.reroll();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            repaint();
            return;
        }

        // Dice Selection
        ArrayList<FoodDice> diceList = foodManager.getBirdFeeder();
        int boxX = 380, boxY = 170;

        for (int i = 0; i < diceList.size(); i++) {
            Rectangle dRect = new Rectangle(boxX + 30 + i * 110, boxY + 30, 100, 100);

            if (dRect.contains(x,y)) {
                FoodDice chosen = foodManager.getDie(i);
                remainingChoices--;

                if (chosen.getFood() == Food.ANY) {
                    choosingMulti = true;
                    multiDice = chosen;
                }

                repaint();
                return;
            }
        }
    }

    private void finishMultiChoice(Food chosen) {
        choosingMulti = false;
        repaint();
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}

