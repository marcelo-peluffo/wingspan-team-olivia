package wingspan.cards.bonusCards;
import java.io.IOException;
import wingspan.core.*;
import wingspan.cards.Card;
import java.util.*;

public class EggBonus extends BonusCard{
    
    private int minEggs;

    public EggBonus(String cardName) throws IOException
    {
        super(cardName + ".jpg");
        if (cardName.equals("BreedingManager"))
        {
            minEggs = 4;
        }
        else if (cardName.equals("Oologist"))
        {
            minEggs = 1;
        }
    }

    public int calculateScore(Player p)
    {
        ArrayList<Card> playerCards = p.getGameBoard().returnAllCards();
        int totalCards = 0;
        for(Card c: playerCards)
        {
            if (c.getCurrentEggs() >= minEggs)
            {
                totalCards++;
            }
        }
        if (minEggs == 4)
            return totalCards;
        else
            return calculateCards(totalCards, 9, 7, 6, 3);
    }
}
