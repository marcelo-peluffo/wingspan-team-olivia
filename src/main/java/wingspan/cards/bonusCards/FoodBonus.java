package wingspan.cards.bonusCards;
import java.io.IOException;
import wingspan.core.*;
import wingspan.cards.Card;
import java.util.*;
import wingspan.enums.Food;

public class FoodBonus extends BonusCard{
    
    Food food;

    public FoodBonus(String cardName) throws IOException
    {
        super(cardName + ".jpg");
        if (cardName.equals("BirdFeeder"))
        {
            food = Food.WHEAT;
        }
        else if (cardName.equals("FisheryManager"))
        {
            food = Food.FISH;
        }
        else if (cardName.equals("OmnivoreSpecialist"))
        {
            food = Food.ANY;
        }
        else if (cardName.equals("FoodWebExpert"))
        {
            food = Food.INVERTEBRATE;
        }
        else if (cardName.equals("Rodentologist"))
        {
            food = Food.RODENT;
        }
        else if (cardName.equals("Viticulturalist"))
        {
            food = Food.BERRY;
        }
    }

    public int calculateScore(Player p)
    {
        ArrayList<Card> playerCards = p.getGameBoard().returnAllCards();
        int totalCards = 0;
        for(Card c: playerCards)
        {
            Food[][] cardFood = c.getBirdInfo().getFoodCost();
            boolean hasFood = false;
            boolean end = false;
            for(Food[] row: cardFood)
            {
                for (Food column: row)
                {
                    if (column == food)
                    {
                        hasFood = true;
                    }
                    if (column != food && food == Food.INVERTEBRATE)
                    {
                        hasFood = false;
                        end = true;
                        break;
                    }
                }
                if (end)
                {
                    break;
                }
            }
            if (hasFood)
            {
                totalCards++;
            }
        }
        if (food == Food.WHEAT)
            return calculateCards(totalCards, 8, 5, 7, 3);
        else if (food == Food.FISH || food == Food.BERRY)
        {
            return calculateCards(totalCards, 4, 2, 7, 3);
        }
        else
        {
            return totalCards * 2;
        }
    }
}
