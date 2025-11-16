package wingspan.food;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;
import java.io.*;
import wingspan.enums.Food;

public class FoodDice {
        private BufferedImage diceFace;
        private Food currentFood;

        private ArrayList<Food> foodChoices;
        private ArrayList<BufferedImage> foodFaces;

        public FoodDice() throws IOException{
            foodChoices = new ArrayList<>();
            foodChoices.add(Food.ANY);
            foodChoices.add(Food.INVERTEBRATE);
            foodChoices.add(Food.WHEAT);
            foodChoices.add(Food.RODENT);
            foodChoices.add(Food.BERRY);
            foodChoices.add(Food.FISH);
            
            BufferedImage choiceImage = ImageIO.read(FoodDice.class.getResource("/Images/MultiDice.jpg"));
            BufferedImage invertebrateImage = ImageIO.read(FoodDice.class.getResource("/Images/InvertebrateDice.jpg"));
            BufferedImage wheatImage = ImageIO.read(FoodDice.class.getResource("/Images/WheatDice.jpg"));
            BufferedImage rodentImage = ImageIO.read(FoodDice.class.getResource("/Images/RodentDice.jpg"));
            BufferedImage berryImage = ImageIO.read(FoodDice.class.getResource("/Images/BerryDice.jpg"));
            BufferedImage fishImage = ImageIO.read(FoodDice.class.getResource("/Images/FishDice.jpg"));

            foodFaces = new ArrayList<>();
            foodFaces.add(choiceImage);
            foodFaces.add(invertebrateImage);
            foodFaces.add(wheatImage);
            foodFaces.add(rodentImage);
            foodFaces.add(berryImage);
            foodFaces.add(fishImage);

            rerollDice();
            }

        public void rerollDice(){
            Random random = new Random();
            int randomNumber = random.nextInt(6);
            currentFood = foodChoices.get(randomNumber);
            diceFace =  foodFaces.get(randomNumber);
        }

        public BufferedImage getImage(){
            return diceFace;
        }

        public Food getFood(){
            return currentFood;
        }
}
