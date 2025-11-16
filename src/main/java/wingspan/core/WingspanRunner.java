package wingspan.core;

import java.io.*;
import wingspan.cards.CardManager;
import wingspan.food.FoodManager;
import wingspan.WingspanFrame;


public class WingspanRunner {
	public static void main(String[]args) throws IOException
	{
		
		for(int i=0; i<4; i++)
		{
			GameState.players.add(new Player());
		}
		try {
			GameState.cardManager = new CardManager();
			GameState.cardManager.initializeBonusCards();
			GameState.foodManager = new FoodManager();
			GameState.goalBoard = new GoalBoard();
		} catch (Exception e) {
			e.printStackTrace();
		}

		WingspanFrame game = new WingspanFrame("Wingspan");
	}
}
