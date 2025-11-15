package wingspan.cards.goals;
import java.io.*;
import wingspan.core.*;
import java.util.*;
import wingspan.cards.Card;

public class WildGoal extends Goal //this class will handle the 2 more unique goals in the game
{
    private boolean totalCards;

    public WildGoal(boolean totalCards, String goalName) throws IOException
    {
        super(goalName + ".jpg");
        this.totalCards = totalCards;
    }

    public int evaluatePlayer(Player p)
    {
        if (totalCards)
        {
            return p.getGameBoard().returnAllCards().size(); //this particular goal simply considers the number of cards the player has
        }
        else //for this goal, it counts sets of 3 eggs across all habitats (in short, the habitat with the min number of eggs = num points the player gets)
        {
            List<Card> forestCards = p.getGameBoard().getForest();
            List<Card> grasslandsCards = p.getGameBoard().getGrasslands();
            List<Card> wetlandsCards = p.getGameBoard().getWetlands();
            int forestEggs = 0;
            int grasslandsEggs = 0;
            int wetlandsEggs = 0;
            for(Card c: forestCards)
            {
                forestEggs += c.getCurrentEggs();
            }
            for(Card c: grasslandsCards)
            {
                grasslandsEggs += c.getCurrentEggs();
            }
            for(Card c: wetlandsCards)
            {
                wetlandsEggs += c.getCurrentEggs();
            }
            return Math.min(forestEggs, Math.min(grasslandsEggs, wetlandsEggs));
        }
    }
}