package wingspan.ui.components;

import wingspan.food.*;
import wingspan.ui.MainPanel;
import wingspan.cards.goals.Goal;
import wingspan.core.GameState;
import wingspan.core.Player;
import wingspan.enums.Food;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FeederComponent extends JPanel implements MouseListener {

    // GoalsComponent
    private Goal[] goals;

    // FoodInventoryComponent
    private Player activePlayer;
    private Map<Food, Integer> foodInventory;
    private Map<Food, BufferedImage> foodToImage;

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

        // FoodInventoryComponent
        activePlayer = GameState.activePlayer;
        foodInventory = activePlayer.getFoodInventory();
        foodToImage = new HashMap<>();

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

        // GoalsComponent
        goals = GameState.goalBoard.getGoals();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        // Left Bird Feeder Image
        g.drawImage(feederImage, 300, 315, 350, 460, null);

        // Remaining Choices Text
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        g.drawString("Remaining choices: " + remainingChoices, 830, 400);

        // Middle Dice Area Box
        int boxX = 660, boxY = 415, boxW = 600, boxH = 250;
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
        rerollButtonRect = new Rectangle(870, 680, 180, 60);
        g.setColor(canReroll ? new Color(250, 230, 90) : new Color(170, 170, 170));
        g.fillRect(rerollButtonRect.x, rerollButtonRect.y, rerollButtonRect.width, rerollButtonRect.height);
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        g.drawString("REROLL", rerollButtonRect.x + 40, rerollButtonRect.y + 40);

        // Multi-choice
        if (choosingMulti) {
            drawMultiChoice(g2);
        }

        // FoodInventoryComponent
        // smooth out shapes and text
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
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2)); // Make the line 2px thick for visibility

        // 1. Vertical Line
        g2.drawLine(lineX, startY, lineX, endY);

        // 2. Top Horizontal Cap (from left of image to the vertical line)
        g2.drawLine(x, startY, lineX + 20, startY);

        // 3. Bottom Horizontal Cap
        g2.drawLine(x, endY, lineX + 20, endY);

        // --- Draw Items ---
        int y = startY + 20;
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
                int textY = y + (imgSize / 2) + (fontSize / 3);

                g2.drawString(String.valueOf(count), textX, textY);

                y += slotHeight + padding;
            }
        }

        // GoalsComponent
        int goalXPos = getWidth() - 400;
        for (Goal goal : goals) {
            g.drawImage(goal.getImage(), goalXPos, getHeight() - 100, 100, 100, null);
            goalXPos += 100;
        }
        int arrowXPos = getWidth() - 450 + GameState.roundNum * 100;
        int[] xPoints = {arrowXPos - 25, arrowXPos, arrowXPos + 25};
        int[] yPoints = {getHeight() - 155, getHeight() - 110, getHeight() - 155};
        g.fillPolygon(xPoints, yPoints, 3);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Goals", getWidth() - 250, getHeight() - 160);

        // RoundCounterComponent
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
    }

    // Draw bottom-left multi-choice box
    private void drawMultiChoice(Graphics2D g) {
        int bx = 3, by = 905, bw = 240, bh = 130;

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

    // Add choice to food inventory
    private void addFoodToInventory(Food food) {
        if (food == null || food == Food.ANY) return;

        int current = foodInventory.getOrDefault(food, 0);
        foodInventory.put(food, current + 1);
    }

    // Check if reroll allowed
    private boolean canRerollNow() {
        // Can only reroll if the player still has choices left
        if (remainingChoices <= 0) return false;

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
            if (multiInvRect != null && multiInvRect.contains(x, y)) {
                finishMultiChoice(Food.INVERTEBRATE);
                return;
            }
            if (multiWheatRect != null && multiWheatRect.contains(x, y)) {
                finishMultiChoice(Food.WHEAT);
                return;
            }
        }

        // Check if the player has exhausted their choices
        if (remainingChoices <= 0) {
            System.out.println("No more food choices remaining this turn.");
            return;
        }

        // Reroll
        if (rerollButtonRect != null && rerollButtonRect.contains(x, y) && canRerollNow()) {
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
        int boxX = 660, boxY = 415;

        for (int i = 0; i < diceList.size(); i++) {
            Rectangle dRect = new Rectangle(boxX + 30 + i * 110, boxY + 30, 100, 100);

            if (dRect.contains(x, y)) {
                FoodDice chosen = foodManager.getDie(i);

                remainingChoices--;

                if (chosen.getFood() == Food.ANY) {
                    // Multi-dice add to food inventory
                    choosingMulti = true;
                    multiDice = chosen;
                } else {
                    // Normal dice add to food inventory
                    addFoodToInventory(chosen.getFood());
                }

                repaint();
                return;
            }
        }
    }

    // after multi-dice choice
    private void finishMultiChoice(Food chosen) {
        choosingMulti = false;
        addFoodToInventory(chosen);
        multiDice = null;
        repaint();
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
