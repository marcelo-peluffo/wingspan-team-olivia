package wingspan.cards.bonusCards;
import java.io.IOException;
import wingspan.core.*;
import wingspan.cards.Card;
import java.util.*;

public class WildBonus extends BonusCard{
    
    private String cardName;

    public WildBonus(String cardName) throws IOException
    {
        super(cardName + ".jpg");
        this.cardName = cardName;
    }

    public int calculateScore(Player p)
    {
        if (cardName.equals("BackyardBirder"))
        {
            ArrayList<Card> playerCards = p.getGameBoard().returnAllCards();
            int totalCards = 0;
            for(Card c: playerCards)
            {
                if (c.getBirdInfo().getVictoryPoints() < 4)
                {
                    totalCards++;
                }
            }
            return calculateCards(totalCards, 6, 5, 6, 3);
        }
        else if (cardName.equals("Ecologist"))
        {
            GameBoard playerBoard = p.getGameBoard();
            List<Card> forest = playerBoard.getForest();
            List<Card> grasslands = playerBoard.getGrasslands();
            List<Card> wetlands = playerBoard.getWetlands();
            int lowestCards = Math.min(forest.size(), Math.min(grasslands.size(), wetlands.size()));
            return lowestCards * 2;
        }
        else if (cardName.equals("VisionaryLeader"))
        {
            int totalCards = p.getHand().size();
            return calculateCards(totalCards, 8, 5, 7, 4);
        }
        return 0; //placeholder to remove error, won't execute this
    }
}
