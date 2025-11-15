package wingspan.core;

import java.io.*;
import wingspan.cards.CardManager;
import wingspan.food.FoodManager;
import wingspan.WingspanFrame;


public class WingspanRunner {
	public static void main(String[]args) throws IOException
	{
		GameState.cardManager = new CardManager();
		GameState.cardManager.initializeBonusCards();
		GameState.foodManager = new FoodManager();
		GameState.goalBoard = new GoalBoard();
		for(int i=0; i<4; i++)
		{
			GameState.players.add(new Player());
		}
		WingspanFrame game = new WingspanFrame("Wingspan");
	}
}
