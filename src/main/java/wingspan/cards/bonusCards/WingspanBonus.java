package wingspan.cards.bonusCards;
import java.io.IOException;
import wingspan.core.*;
import wingspan.cards.Card;
import java.util.*;

public class WingspanBonus extends BonusCard{
    
    private int wingSpan;
    private boolean higherThan;

    public WingspanBonus(String cardName) throws IOException
    {
        super(cardName + ".jpg");
        if (cardName.equals("LargeBirdSpecialist"))
        {
            wingSpan = 65;
            higherThan = true;
        }
        else if (cardName.equals("PasserineSpecialist"))
        {
            wingSpan = 30;
            higherThan = false;
        }
    }

    public int calculateScore(Player p)
    {
        ArrayList<Card> playerCards = p.getGameBoard().returnAllCards();
        int totalCards = 0;
        for(Card c: playerCards)
        {
            int cardSpan = c.getBirdInfo().getWingSpan();
            if (higherThan)
            {
                if (cardSpan > wingSpan)
                {
                    totalCards++;
                }
            }
            else
            {
                if (cardSpan < wingSpan)
                {
                    totalCards--;
                }
            }
        }
        return calculateCards(totalCards, 6, 4, 7, 4);
    }
}
