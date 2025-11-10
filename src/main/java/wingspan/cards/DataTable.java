package wingspan.cards;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import javax.imageio.ImageIO;
import com.fasterxml.jackson.databind.ObjectMapper;
import wingspan.WingspanFrame;
import wingspan.cards.behavior.*;
import wingspan.enums.*;

class BirdInfoJson {
    public String name;
    public List<Habitat> habitats;
    public List<List<Food>> food;
    public int victoryPoints;
    public NestType nestType;
    public int maxEggs;
    public int wingSpan;
    public Color color;
    public BehaviorParameters behavior; // <-- Jackson will auto-fill this!
    public String image;
}

public class DataTable {

    private static Food[][] getFoodArray(BirdInfoJson bj) {
        Food[][] foodArray = new Food[bj.food.size()][];
        for (int r = 0; r < bj.food.size(); r++) {
            List<Food> row = bj.food.get(r);
            foodArray[r] = new Food[row.size()];
            for (int c = 0; c < row.size(); c++) {
                foodArray[r][c] = row.get(c);
            }
        }
        return foodArray;
    }

    private static EnumSet<Habitat> getHabitatSet(BirdInfoJson bj) {
        EnumSet<Habitat> habitats = EnumSet.noneOf(Habitat.class);
        for (Habitat h : bj.habitats) habitats.add(h);
        return habitats; // fixed: return statement
    }

    public static void initialize() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream jsonStream = DataTable.class.getResourceAsStream("/wingspan/cards/birds.json");
            if (jsonStream == null) {
                throw new RuntimeException("Could not find birds.json in resources!");
            }

            BirdInfoJson[] birds = mapper.readValue(jsonStream, BirdInfoJson[].class);

            for (BirdInfoJson bj : birds) {
                EnumSet<Habitat> habitats = getHabitatSet(bj);
                Food[][] foodArray = getFoodArray(bj);
                NestType nestType = bj.nestType;
                Color color = bj.color;

                String imagePath = "/Images/" + bj.image;
                InputStream imgStream = WingspanFrame.class.getResourceAsStream(imagePath);
                BufferedImage birdImage = ImageIO.read(imgStream);

                PowerBehavior behavior = BehaviorFactory.createBehavior(bj.behavior);

                BirdInfo birdInfo = new BirdInfo(
                        bj.name, habitats, foodArray, bj.victoryPoints,
                        nestType, bj.maxEggs, bj.wingSpan, color,
                        behavior
                );
                CardManager.birdInfos.add(birdInfo);
                CardManager.birdCards.add(new Card(birdInfo, birdImage));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load birds JSON", e);
        }
    }
}
