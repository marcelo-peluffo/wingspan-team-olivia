package wingspan.core;

import java.io.*;
import java.util.*;
import wingspan.cards.CardManager;
import wingspan.food.FoodManager;
import wingspan.WingspanFrame;
import wingspan.cards.DataTable;


public class WingspanRunner {
	public static void main(String[]args) throws IOException
	{
		GameState.cardManager = new CardManager();
		GameState.cardManager.initializeBonusCards();
		GameState.foodManager = new FoodManager();
		GameState.goalBoard = new GoalBoard();
		GameState.players = new ArrayList<>();
		DataTable.initialize();
		for(int i=0; i<4; i++)
		{
			GameState.players.add(new Player());
		}
		

		WingspanFrame game = new WingspanFrame("Wingspan");
	}
}
