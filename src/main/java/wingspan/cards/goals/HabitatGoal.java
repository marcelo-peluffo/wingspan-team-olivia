package wingspan.cards.goals;
import wingspan.enums.Habitat;
import java.io.*;
import wingspan.core.*;
import java.util.*;
import wingspan.cards.Card;

public class HabitatGoal extends Goal //this class will handle 2 types of goals: eggs in a habitat / cards in a habitat
{
    private Habitat habitat;
    private boolean isCardGoal; //if the goal checks cards in habitat, this variable = true, otherwise false

    public HabitatGoal(Habitat habitat, boolean isCardGoal, String goalPath) throws IOException
    {
        super(goalPath + ".jpg");
        this.habitat = habitat;
        this.isCardGoal = isCardGoal;
    }

    public int evaluatePlayer(Player p)
    {
        List<Card> habitatCards; //get the player's respective habitat cards
        if (habitat == Habitat.FOREST)
        {
            habitatCards = p.getGameBoard().getForest();
        }
        else if (habitat == Habitat.GRASSLANDS)
        {
            habitatCards = p.getGameBoard().getGrasslands();
        }
        else
        {
            habitatCards = p.getGameBoard().getWetlands();
        }
        if (isCardGoal)
        {
            return habitatCards.size(); //if the goal check for num of cards in the habitat, simply return habitatCards.size()
        }
        else //if the goal checks for total num of eggs in habitat, loop through and return total amount of eggs
        {
            int totalEggs = 0;
            for(Card c: habitatCards)
            {
                totalEggs += c.getCurrentEggs();
            }
            return totalEggs;
        }
    }
}