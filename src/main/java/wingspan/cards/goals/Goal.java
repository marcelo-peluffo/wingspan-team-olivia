package wingspan.cards.goals;
import java.awt.image.BufferedImage;
import wingspan.core.Player;

public class Goal {

    private final BufferedImage goalImage; // what the player will be shown
    private final String goalString; // used to identify what the goal actually is

    public Goal(BufferedImage goalImage, String goalString) 
    {
        this.goalImage = goalImage;
        this.goalString = goalString;
        System.out.println("The Goal " + goalString + " was created.");
    }

    public int evaluateGoal(Player p)
    {
        /*
         *  
         * 
         * if goal met:
         *      player points + amount
         *  else:
         *      no points added (or points deducted depending on whether that is a rule)
         * 
         * 
         */

        return 0; // arbitrary return value for now
    }

    public BufferedImage getImage()
    {
        return this.goalImage;
    }

    public String getGoalString()
    {
        return this.goalString;
    }

}
// Goals: 
// - Egg in certain habitat
// - Card in certain habitat
// - Eggs on certain nests
// - Birds with certain nests that have eggs
// - sets of 3 eggs
// - Total birds
