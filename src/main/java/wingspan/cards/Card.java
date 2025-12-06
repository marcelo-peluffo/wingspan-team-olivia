package wingspan.cards;
import java.awt.image.BufferedImage;
import java.util.*;
import wingspan.enums.*;
import wingspan.core.*;

public class Card {
    private BirdInfo birdInfo;
    private BufferedImage cardImage;
    private int currentEggs;
    private final List<Card> tuckedCards;
    private final List<Food> foodTokens;
    private boolean hasExecutedPower;

    public Card(BirdInfo birdInfo, BufferedImage cardImage)
    {
        this.birdInfo = birdInfo;
        this.cardImage = cardImage;
        currentEggs = 0;
        tuckedCards = new ArrayList<>();
        foodTokens = new ArrayList<>();
    }

    public Card(BirdInfo birdInfo, BufferedImage cardImage, int currentEggs, ArrayList<Card> tuckedCards, ArrayList<Food> foodTokens)
    {
        this.birdInfo = birdInfo;
        this.cardImage = cardImage;
        this.currentEggs = currentEggs;
        this.tuckedCards = tuckedCards;
        this.foodTokens = foodTokens;
        this.hasExecutedPower = false;
    }

    public BirdInfo getBirdInfo() { return birdInfo; }
    public BufferedImage getCardImage() { return cardImage; }

    public void triggerPower()
    {
        hasExecutedPower = !hasExecutedPower;
    }

    public boolean hasActivatedPower()
    {
        return hasExecutedPower;
    }

    public int countAnyFood() //returns how many tokens of any food type this bird costs
    {
        Food[][] foodCost = this.getBirdInfo().getFoodCost();
        int count = 0;
        for(int i=0; i<foodCost.length; i++)
        {
            for(int j=0; j<foodCost[i].length; j++)
            {
                if (foodCost[i][j] == Food.ANY)
                {
                    count++;
                }
            }
        }
        if (foodCost.length == 1 && foodCost[0].length > 1)
        {
            return 1;
        }
        return count;
    }

    public boolean hasAnyFood()
    {
        Food[][] foodCost = this.getBirdInfo().getFoodCost();
        for(int i=0; i<foodCost.length; i++)
        {
            if (foodCost[i][0] == Food.ANY)
            {
                return true;
            }
        }
        return false;
    }

    public boolean canPayFoodCost(Player p) //returns true if player has all food tokens required to play this card
    {
        Food[][] foodCost = this.getBirdInfo().getFoodCost();
        HashMap<Food, Integer> playerInventory = new HashMap<Food, Integer>();
        for(Food f: p.getFoodInventory().keySet())
        {
            playerInventory.put(f, p.getFoodInventory().get(f));
        }
        if (foodCost.length == 0)
        {
            return true;
        }
        if (foodCost.length == 1 && foodCost[0].length > 1)
        {
            for(int j=0; j<foodCost[0].length; j++)
            {
                if (playerInventory.get(foodCost[0][j]) > 0)
                {
                    return true;
                }
            }
        }
        int numAny = 0;
        for(int i=0; i<foodCost.length; i++)
        {
            if (foodCost[i][0] == Food.ANY)
            {
                numAny++;
            }
            else
            {
                if (playerInventory.get(foodCost[i][0]) == 0)
                {
                    return false;
                }
                else
                {
                    playerInventory.put(foodCost[i][0], playerInventory.get(foodCost[i][0]) - 1);
                }
            }
        }
        int numRemainingFood = 0;
        for(Food f: playerInventory.keySet())
        {
            numRemainingFood += playerInventory.get(f);
        }
        return numRemainingFood >= numAny;
    }

    public boolean couldPayFoodCost(Player p) //returns true if player could theoretically play this card through the exchange rule when playing a bird
    {
        Food[][] foodCost = this.getBirdInfo().getFoodCost();
        HashMap<Food, Integer> playerInventory = new HashMap<Food, Integer>();
        for(Food f: p.getFoodInventory().keySet())
        {
            playerInventory.put(f, p.getFoodInventory().get(f));
        }
        if (foodCost.length == 0)
        {
            return true;
        }
        if (foodCost.length == 1 && foodCost[0].length > 1)
        {
            for(int j=0; j<foodCost[0].length; j++)
            {
                if (playerInventory.get(foodCost[0][j]) > 0)
                {
                    return true;
                }
            }
        }
        int numAny = 0;
        for(int i=0; i<foodCost.length; i++)
        {
            if (foodCost[i][0] == Food.ANY)
            {
                numAny++;
            }
            else
            {
                if (playerInventory.get(foodCost[i][0]) == 0)
                {
                    numAny += 2;
                }
                else
                {
                    playerInventory.put(foodCost[i][0], playerInventory.get(foodCost[i][0]) - 1);
                }
            }
        }
        int numRemainingFood = 0;
        for(Food f: playerInventory.keySet())
        {
            numRemainingFood += playerInventory.get(f);
        }
        return numRemainingFood >= numAny;
    }

    public void payFood(Player p)
    {
        Food[][] foodCost = this.getBirdInfo().getFoodCost();
        Map<Food, Integer> foodInventory = p.getFoodInventory();
        for(int i=0; i<foodCost.length; i++)
        {
            if (foodCost[i].length == 1 && foodCost[i][0] != Food.ANY)
            {
                foodInventory.put(foodCost[i][0], foodInventory.get(foodCost[i][0]) - 1);
            }
        }
    }

    public void setBirdInfo(BirdInfo newBirdInfo)
    {
        birdInfo = newBirdInfo;
    }

    public void setCardImage(BufferedImage newCardImage)
    {
        cardImage = newCardImage; // if we want to use templates instead of 170 separate card objs
    }

    public boolean addEggs(int amount)
    {
        if (currentEggs + amount <= birdInfo.getMaxEggs())
        {
            currentEggs += amount;
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean removeEggs(int amount)
    {
        if (currentEggs == 0)
        {
            return false;
        }
        else
        {
            currentEggs -= amount;
            return true;
        }
    }

    public int getCurrentEggs()
    {
        return currentEggs;
    }

    public boolean isAtMaxEggs()
    {
        return currentEggs == birdInfo.getMaxEggs();
    }

    public boolean hasNoEggs()
    {
        return currentEggs == 0;
    }

    public void tuckCard(Card c)
    {
        this.tuckedCards.add(c);
    }

    public List<Card> getTuckedCards() 
    {
        return tuckedCards;
    }

    public List<Food> getFoodTokens() 
    {
        return foodTokens;
    }

    public void addFoodToken(Food f)
    {
        foodTokens.add(f);
    }

}
