package wingspan.cards;
import java.util.*;

public class CardManager {
	public static HashSet<Card> birdCards = new HashSet<>();
	public static HashSet<BirdInfo> birdInfos = new HashSet<>();
	public static HashSet<BonusCard> bonusCards = new HashSet<>();
	public static ArrayList<Card> faceUpCards = new ArrayList<>();
	
	// returns and removes a random card from birdCards
	public Card getRandomCard()
	{
		Iterator<Card> iter = birdCards.iterator();
		int index = (int)(Math.random() * birdCards.size());
		for(int i=0; i<index; i++)
		{
			iter.next();
		}
		Card c = iter.next();
		iter.remove();
		return c;
	}
	
	//returns and removes a random card from bonusCards
	public BonusCard getRandomBonusCard()
	{
		Iterator<BonusCard> iter = bonusCards.iterator();
		int index = (int)(Math.random() * bonusCards.size());
		for(int i=0; i<index; i++)
		{
			iter.next();
		}
		BonusCard c = iter.next();
		iter.remove();
		return c;
	}
	
	// returns and removes a card from faceUpCards based on a passed in index
	public Card getVisibleCard(int index)
	{
		return faceUpCards.remove(index);
	}
	
	//refills faceUpCards with randomly selected bird cards until there are 3 in the list
	public void refillVisibleCards()
	{
		while (faceUpCards.size() < 3)
		{
			faceUpCards.add(getRandomCard());
		}
	}
}
