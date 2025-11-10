package wingspan.cards;
import wingspan.cards.behavior.*;
import wingspan.enums.*;

import java.util.*;

public class BirdInfo {
    private final String name; // Bird name
    private final EnumSet<Habitat> habitats; // Habitats: forest, grassland, wetland
    private final Food[][] foodCost; // Food cost: fish, invertebrate, grain, fruit, rodent, wild
    private final int victoryPoints; // Victory points
    private final NestType nestType; // Nest type: bowl, cavity, platform, ground, star, none
    private final int maxEggs; // Maximum number of eggs
    private final int wingSpan; // Wingspan in cm
    private final Color powerColor; // Power color: brown, pink, white, none
    private final PowerBehavior behavior;

    public BirdInfo(String name, EnumSet<Habitat> habitats, Food[][] foodCost, int victoryPoints,
                    NestType nestType, int maxEggs, int wingSpan, Color powerColor, PowerBehavior behavior) {
        this.name = name;
        this.habitats = habitats;
        this.foodCost = foodCost;
        this.victoryPoints = victoryPoints;
        this.nestType = nestType;
        this.maxEggs = maxEggs;
        this.wingSpan = wingSpan;
        this.powerColor = powerColor;
        this.behavior = behavior;
    }

    public String getName() { return name; }
    public EnumSet<Habitat> getHabitats() { return habitats; }
    public Food[][] getFoodCost() { return foodCost; }
    public int getVictoryPoints() { return victoryPoints; }
    public NestType getNestType() { return nestType; }
    public int getMaxEggs() { return maxEggs; }
    public int getWingSpan() { return wingSpan; }
    public Color getPowerColor() { return powerColor; }
    public PowerBehavior getBehavior() { return behavior; }

    @Override
    public String toString() {
        return "BirdInfo{" +
                "name='" + name + '\'' +
                ", habitats=" + habitats +
                ", foodCost=" + Arrays.deepToString(foodCost) +
                ", victoryPoints=" + victoryPoints +
                ", nestType=" + nestType +
                ", maxEggs=" + maxEggs +
                ", wingSpan=" + wingSpan +
                ", powerColor=" + powerColor +
                ", behavior=" + behavior +
                '}';
    }
}
