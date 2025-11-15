package wingspan.cards.bonusCards;
import java.io.IOException;
import wingspan.core.*;
import wingspan.cards.Card;
import java.util.*;
import wingspan.enums.Habitat;

public class HabitatBonus extends BonusCard{
    
    Habitat habitat;

    public HabitatBonus(String cardName) throws IOException
    {
        super(cardName + ".jpg");
        if (cardName.equals("Forester"))
        {
            habitat = Habitat.FOREST;
        }
        else if (cardName.equals("PrarieManager"))
        {
            habitat = Habitat.GRASSLANDS;
        }
        else if (cardName.equals("WetlandScientist"))
        {
            habitat = Habitat.WETLANDS;
        }
    }

    public int calculateScore(Player p)
    {
        ArrayList<Card> playerCards = p.getGameBoard().returnAllCards();
        int totalCards = 0;
        for(Card c: playerCards)
        {
            if (c.getBirdInfo().getHabitats().contains(habitat) && c.getBirdInfo().getHabitats().size() == 1)
            {
                totalCards++;
            }
        }
        if (habitat == Habitat.FOREST)
        {
            return calculateCards(totalCards, 5, 3, 5, 4);
        }
        else if (habitat == Habitat.GRASSLANDS)
        {
            return calculateCards(totalCards, 4, 2, 8, 3);
        }
        else
        {
            return calculateCards(totalCards, 5, 3, 7, 3);
        }
    }
}
