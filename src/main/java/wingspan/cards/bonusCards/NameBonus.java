package wingspan.cards.bonusCards;
import java.io.IOException;
import wingspan.core.*;
import wingspan.cards.Card;
import java.util.*;

public class NameBonus extends BonusCard{
    private String[] nameConditions;

    public NameBonus(String cardName) throws IOException
    {
        super(cardName + ".jpg");
        if (cardName.equals("Anatomist"))
        {
            String[] names = {"beak", "belly", "bill", "breast", "cap", "chin", "collar", "crest", "crown", "eye", "face", "head", "neck", "rump", "shoulder", "tail", "throat", "wing"};
            nameConditions = names;
        }
        else if (cardName.equals("Cartographer"))
        {
            String[] names = {"american", "atlantic", "baltimore", "california", "canada", "carolina", "chihuahua", "eastern", "inca", "mississippi", "mountain", "northern", "prairie", "sandhill", "savannah", "western"};
            nameConditions = names;
        }
        else if (cardName.equals("Photographer"))
        {
            String[] names = {"ash", "black", "blue", "bronze", "brown", "cerulean", "chestnut", "ferruginous", "gold", "gray", "green", "indigo", "lazuli", "purple", "red", "rose", "roseate", "ruby", "ruddy", "rufous", "snowy", "violet", "white", "yellow"};
            nameConditions = names;
        }
        else if (cardName.equals("Historian"))
        {
            String[] names = {"'s"};
            nameConditions = names;
        }
    }

    public int calculateScore(Player p)
    {
        ArrayList<Card> playerCards = p.getGameBoard().returnAllCards();
        int totalCards = 0;
        for(Card c: playerCards)
        {
            for(String s: nameConditions)
            {
                if (c.getBirdInfo().getName().toLowerCase().indexOf(s) > -1)
                {
                    totalCards++;
                }
            }
        }
        if (nameConditions[0].equals("ash"))
        {
            return calculateCards(totalCards, 6, 4, 7, 3);
        }
        else if (nameConditions[0].equals("'s"))
        {
            return totalCards * 2;
        }
        else
        {
            return calculateCards(totalCards, 4, 2, 7, 3);
        }
    }
}
