package wingspan.ui.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import wingspan.core.GameState;
import wingspan.core.Player;
import wingspan.enums.Food;

public class FoodInventoryComponent extends JPanel {

    private Player activePlayer;
    private Map<Food, Integer> foodInventory;
    private Map<Food, BufferedImage> foodToImage;

    public FoodInventoryComponent() {
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
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Use Graphics2D for better line control (thickness)
        Graphics2D g2 = (Graphics2D) g;
        
        // smooth out shapes and text
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int numItems = foodInventory.size();
        if (numItems == 0) return;

        int padding = 10;
        int x = padding;
        int panelWidth = getWidth();
        
        // Calculate heights
        int slotHeight = (getHeight() - padding * (numItems + 1)) / numItems;
        int imgSize = Math.min(slotHeight, panelWidth / 2);

        // --- Calculate Coordinates for Lines ---
        int startY = padding;
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
                // (y + imgSize/2) is center, + (fontSize/3) roughly centers the text baseline
                int textY = y + (imgSize / 2) + (fontSize / 3);
                
                g2.drawString(String.valueOf(count), textX, textY);

                y += slotHeight + padding;
            }
        }
    }
}