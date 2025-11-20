package wingspan.core;

import java.io.*;
import java.util.*;
import wingspan.cards.CardManager;
import wingspan.food.FoodManager;
import wingspan.WingspanFrame;
import wingspan.cards.DataTable;
import wingspan.enums.*;


public class WingspanRunner {

	public static void main(String[]args) throws IOException
	{
		GameState.initialize();
		DataTable.initialize();
		for(int i=0; i<4; i++)
		{
			GameState.players.add(new Player());
		}
		GameState.activePlayer = GameState.players.get(0);
		for(int i=0; i<4; i++)
		{
			GameState.activePlayer.getGameBoard().addCard(GameState.cardManager.getRandomCard(), Habitat.FOREST); //remove this later, this is for testing
		}
		for(int i=0; i<2; i++)
		{
			GameState.activePlayer.getGameBoard().addCard(GameState.cardManager.getRandomCard(), Habitat.GRASSLANDS); //remove this later, this is for testing
		}
		for(int i=0; i<3; i++)
		{
			GameState.activePlayer.getGameBoard().addCard(GameState.cardManager.getRandomCard(), Habitat.WETLANDS); //remove this later, this is for testing
		}
		WingspanFrame game = new WingspanFrame("Wingspan");
	}
}
