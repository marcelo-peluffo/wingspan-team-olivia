package wingspan.cards.goals;
import wingspan.enums.NestType;
import java.io.*;
import wingspan.core.*;
import java.util.*;
import wingspan.cards.Card;

public class EggsNestGoal extends Goal //this class will handle the goals that involve checking cards with eggs on a certain nest type
{
    private NestType nestType;
    private boolean countMultiple; //if the goal counts multiple eggs on one cards, this variable = true

    public EggsNestGoal(NestType nestType, boolean countMultiple, String goalPath) throws IOException
    {
        super(goalPath);
        this.nestType = nestType;
        this.countMultiple = countMultiple;
    }

    public int evaluatePlayer(Player p)
    {
        List<Card> allPlayerCards = p.getGameBoard().returnAllCards(); //get all the player's cards
        int total = 0;
        for(Card c: allPlayerCards) //loop through all cards, if card's nest type matches goal's, either add all the current eggs or add 1 based on the specific goal
        {
            if (c.getBirdInfo().getNestType() == nestType || c.getBirdInfo().getNestType() == NestType.STAR)
            {
                if (countMultiple)
                {
                    total += c.getCurrentEggs();
                }
                else
                {
                    if (c.getCurrentEggs() > 0)
                    {
                        total++;
                    }
                }
            }
        }
        return total;
    }
}