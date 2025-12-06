package wingspan.cards;
import java.util.*;
import wingspan.cards.bonusCards.*;
import java.io.*;

import wingspan.cards.bonusCards.BonusCard;

public class CardManager {
	public static Set<Card> birdCards = new HashSet<>();
	public static Set<BirdInfo> birdInfos = new HashSet<>();
	public static Set<BonusCard> bonusCards = new HashSet<>();
	public List<Card> faceUpCards = new ArrayList<>();

	public CardManager()
	{
		for(int i=0; i<3; i++)
		{
			faceUpCards.add(null);
		}
	}

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

	//returns the specified card, this will only be used for testing
	public Card getSpecifiedCard(String name)
	{
		for(Card c: birdCards)
		{
			if (c.getBirdInfo().getName().equals(name))
			{
				return c;
			}
		}
		return null;
	}

	public List<Card> getFaceUpCards()
	{
		return faceUpCards;
	}

	public boolean initializeBonusCards() throws IOException
	{
		bonusCards.add(new NameBonus("Anatomist"));
		bonusCards.add(new WildBonus("BackyardBirder"));
		bonusCards.add(new AbilityBonus("BirdCounter"));
		bonusCards.add(new FoodBonus("BirdFeeder"));
		bonusCards.add(new EggBonus("BreedingManager"));
		bonusCards.add(new NameBonus("Cartographer"));
		bonusCards.add(new WildBonus("Ecologist"));
		bonusCards.add(new NestBonus("EnclosureBuilder"));
		bonusCards.add(new AbilityBonus("Falconer"));
		bonusCards.add(new FoodBonus("FisheryManager"));
		bonusCards.add(new FoodBonus("FoodWebExpert"));
		bonusCards.add(new HabitatBonus("Forester"));
		bonusCards.add(new NameBonus("Historian"));
		bonusCards.add(new WingspanBonus("LargeBirdSpecialist"));
		bonusCards.add(new NestBonus("NestBoxBuilder"));
		bonusCards.add(new FoodBonus("OmnivoreSpecialist"));
		bonusCards.add(new EggBonus("Oologist"));
		bonusCards.add(new WingspanBonus("PasserineSpecialist"));
		bonusCards.add(new NameBonus("Photographer"));
		bonusCards.add(new NestBonus("PlatformBuilder"));
		bonusCards.add(new HabitatBonus("PrarieManager"));
		bonusCards.add(new FoodBonus("Rodentologist"));
		bonusCards.add(new WildBonus("VisionaryLeader"));
		bonusCards.add(new FoodBonus("Viticulturalist"));
		bonusCards.add(new HabitatBonus("WetlandScientist"));
		bonusCards.add(new NestBonus("WildlifeGardener"));
		System.out.println("All bonus cards successfully initialized");
		return true;
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
		Card selectedCard = faceUpCards.get(index);
		faceUpCards.set(index, null);
		return selectedCard;
	}
	
	//refills faceUpCards with randomly selected bird cards until there are 3 in the list
	public void refillVisibleCards()
	{
		for(int i=0; i<faceUpCards.size(); i++)
		{
			if (faceUpCards.get(i) == null)
			{
				faceUpCards.set(i, getRandomCard());
			}
		}
	}
}
