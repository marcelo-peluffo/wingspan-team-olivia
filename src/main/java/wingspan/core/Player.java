package wingspan.core;
import wingspan.cards.*;
import wingspan.cards.bonusCards.BonusCard;
import wingspan.enums.Food;

import java.util.*;
import java.io.*;

public class Player {
	private GameBoard gameBoard;
	private List<Card> cards;
	private Card cardToPlay;
	private List<BonusCard> bonusCards;
	private Map<Food, Integer> foodInventory;
	private int actionsRemaining;
	private int playerNum;
	private int goalScore;
	
	public Player() throws IOException
	{
		try {
			gameBoard = new GameBoard();
		} catch (Exception e) {
			e.printStackTrace();
		}
		cards = new ArrayList<>();
		bonusCards = new ArrayList<>();
		foodInventory = new EnumMap<>(Food.class);
		
		for (Food f : Food.values())
		{
			foodInventory.put(f, 0);
		}
		goalScore = 0;
		resetActionsRemaining(1);
	}
	
	public void addGoalScore(int amount)
	{
		goalScore += amount;
	}

	public int getGoalScore()
	{
		return goalScore;
	}

	public void addCard(Card c)
	{
		cards.add(c);
	}
	
	public void removeCard(Card c)
	{
		cards.remove(cards.indexOf(c));
	}
	
	public void addBonusCard(BonusCard c)
	{
		bonusCards.add(c);
	}

	public Card getCardToPlay()
	{
		return cardToPlay;
	}

	public void setCardToPlay(Card c)
	{
		cardToPlay = c;
	}

	public List<Card> getHand()
	{
		return this.cards;
	}
	
	public GameBoard getGameBoard()
	{
		return gameBoard;
	}

	public void addFood(Food food, int amount)
	{
		foodInventory.put(food, foodInventory.get(food) + amount);
	}
	
	public int getActionsRemaining()
	{
		return actionsRemaining;
	}


	public void decreaseActionsRemaining()
	{
		actionsRemaining--;
	}
	
	
	public void resetActionsRemaining(int round)
	{
		switch(round)
		{
			case 1:
				actionsRemaining = 8;
				break;
			case 2:
				actionsRemaining = 7;
				break;
			case 3:
				actionsRemaining = 6;
				break;
			case 4:
				actionsRemaining = 5;
				break;
		}
	}
	
	public Map<Food, Integer> getFoodInventory()
	{
		return foodInventory;
	}

	public List<BonusCard> getBonusCards()
	{
		return bonusCards;
	}

	public int getTotalEggsAmount()
	{
		int total = 0;
		for(Card c: gameBoard.getForest())
		{
			total += c.getCurrentEggs();
		}
		for(Card c: gameBoard.getGrasslands())
		{
			total += c.getCurrentEggs();
		}
		for(Card c: gameBoard.getWetlands())
		{
			total += c.getCurrentEggs();
		}
		return total;
	}

	public int[] getAllScores() // returns an array of scores used to determine final score in the following order: Birds, Bonus Cards, Goals, Eggs, Cached food, tucked cards, total score
	{
		int[] scores = new int[7];
		for (Card c: gameBoard.returnAllCards())
		{
			scores[0] += c.getBirdInfo().getVictoryPoints();
			scores[3] += c.getCurrentEggs();
			scores[4] += c.getFoodTokens().size();
			scores[5] += c.getTuckedCards().size();
		}
		for(BonusCard c: bonusCards)
		{
			scores[1] += c.calculateScore(this);
		}
		scores[2] = goalScore;
		scores[6] = scores[0] + scores[1] + scores[2] + scores[3] + scores[4] + scores[5];
		return scores;
	}
}
