package wingspan.cards.bonusCards;
import java.io.IOException;
import wingspan.core.*;
import wingspan.cards.Card;
import java.util.*;
import wingspan.enums.NestType;

public class NestBonus extends BonusCard{
    
    private NestType nestType;

    public NestBonus(String cardName) throws IOException
    {
        super(cardName + ".jpg");
        if (cardName.equals("EnclosureBuilder"))
        {
            nestType = NestType.GROUND;
        }
        else if (cardName.equals("NestBoxBuilder"))
        {
            nestType = NestType.CAVITY;
        }
        else if (cardName.equals("PlatformBuilder"))
        {
            nestType = NestType.PLATFORM;
        }
        else if (cardName.equals("WildlifeGardener"))
        {
            nestType = NestType.BOWL;
        }
    }

    public int calculateScore(Player p)
    {
        ArrayList<Card> playerCards = p.getGameBoard().returnAllCards();
        int totalCards = 0;
        for(Card c: playerCards)
        {
            if (c.getBirdInfo().getNestType() == nestType || c.getBirdInfo().getNestType() == NestType.STAR)
            {
                totalCards++;
            }
        }
        return calculateCards(totalCards, 6, 4, 7, 4);
    }
}
