package wingspan.core;

import java.io.*;
import java.util.*;
import wingspan.cards.CardManager;
import wingspan.food.FoodManager;
import wingspan.WingspanFrame;
import wingspan.cards.DataTable;
import wingspan.enums.*;
import java.awt.Color;


public class WingspanRunner {

	public static void main(String[]args) throws IOException
	{
		DataTable.initialize();
		GameState.initialize();
		for(int i=0; i<4; i++)
		{
			GameState.players.add(new Player());
		}
		GameState.actionCubeColors = new HashMap<>();
        GameState.actionCubeColors.put(GameState.players.get(0), Color.RED);
        GameState.actionCubeColors.put(GameState.players.get(1), new Color(71, 0, 201));
        GameState.actionCubeColors.put(GameState.players.get(2), new Color(0, 89, 19));
        GameState.actionCubeColors.put(GameState.players.get(3), Color.BLUE);
		GameState.activePlayer = GameState.players.get(0);
		
		// Lay Eggs testing purposes
		CardManager cm = new CardManager();
		GameState.activePlayer.getGameBoard().addCard(cm.getRandomCard(), Habitat.FOREST);
		GameState.activePlayer.getGameBoard().addCard(cm.getRandomCard(), Habitat.FOREST);
		GameState.activePlayer.getGameBoard().addCard(cm.getRandomCard(), Habitat.GRASSLANDS);
		GameState.activePlayer.getGameBoard().addCard(cm.getRandomCard(), Habitat.GRASSLANDS);
		GameState.activePlayer.getGameBoard().addCard(cm.getRandomCard(), Habitat.GRASSLANDS);
		GameState.activePlayer.getGameBoard().addCard(cm.getRandomCard(), Habitat.WETLANDS);
		
		GameState.activePlayer.addFood(Food.BERRY, 1);
		GameState.activePlayer.addFood(Food.WHEAT, 1);

		WingspanFrame game = new WingspanFrame("Wingspan");
	}
}
