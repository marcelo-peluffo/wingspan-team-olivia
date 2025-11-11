package wingspan.cards;
import java.awt.image.BufferedImage;
import java.util.*;
import wingspan.enums.*;


public class Card {
    private BirdInfo birdInfo;
    private BufferedImage cardImage;
    private int currentEggs;
    private final List<Card> tuckedCards;
    private final List<Food> foodTokens;

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
    }

    public BirdInfo getBirdInfo() { return birdInfo; }
    public BufferedImage getCardImage() { return cardImage; }

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

    public List<Card> getTuckedCards() 
    {
        return tuckedCards;
    }

    public List<Food> getFoodTokens() 
    {
        return foodTokens;
    }

}
