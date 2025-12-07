package wingspan.cards.bonusCards;
import java.io.IOException;
import wingspan.core.*;
import wingspan.cards.Card;
import java.util.*;

public class AbilityBonus extends BonusCard{
    
    private String[] behaviorTypes;

    public AbilityBonus(String cardName) throws IOException
    {
        super(cardName + ".jpg");
        if (cardName.equals("BirdCounter"))
        {
            behaviorTypes = new String[2];
            behaviorTypes[0] = "TuckCardBehavior";
            behaviorTypes[1] = "DiscardFoodBehavior";
        }
        else if (cardName.equals("Falconer"))
        {
            behaviorTypes = new String[2];
            behaviorTypes[0] = "RollDiceBehavior";
            behaviorTypes[1] = "WingspanBehavior";
        }
    }

    public int calculateScore(Player p)
    {
        ArrayList<Card> playerCards = p.getGameBoard().returnAllCards();
        int totalCards = 0;
        for(Card c: playerCards)
        {
            for(String behavior: behaviorTypes)
            {
                if (c.getBirdInfo().getBehavior().describe().equals(behavior))
                {
                    totalCards++;
                }
            }
        }
        return 2 * totalCards;
    }
}
