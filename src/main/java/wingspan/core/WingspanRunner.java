package wingspan.core;

import java.io.*;
import java.util.*;
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
        GameState.actionCubeColors.put(GameState.players.get(3), new Color(158, 140, 0));
		GameState.activePlayer = GameState.players.get(0);
		// ----------------- all code between these lines are for testing and should be removed before the game is finalized----------
		for(Player p: GameState.players)
		{
			int r = (int)(Math.random() * 6);
			for(int i=0; i<r; i++)
			{
				p.getGameBoard().addCard(GameState.cardManager.getRandomCard(), Habitat.FOREST);

			}
			r = (int)(Math.random() * 6);
			for(int i=0; i<r; i++)
			{
				p.getGameBoard().addCard(GameState.cardManager.getRandomCard(), Habitat.GRASSLANDS);

			}
			r = (int)(Math.random() * 6);
			for(int i=0; i<r; i++)
			{
				p.getGameBoard().addCard(GameState.cardManager.getRandomCard(), Habitat.WETLANDS);
				
			}
		}
		GameState.activePlayer.getGameBoard().getWetlands().get(0).addEggs(1);
		for(int i=0; i<5; i++) {
		GameState.players.get(0).addCard(GameState.cardManager.getRandomCard());
		GameState.players.get(0).addFood(Food.FISH, 5);
		GameState.players.get(0).addBonusCard(GameState.cardManager.getRandomBonusCard());
		GameState.players.get(1).addCard(GameState.cardManager.getRandomCard());
		GameState.players.get(1).addFood(Food.FISH, 5);
		GameState.players.get(1).addBonusCard(GameState.cardManager.getRandomBonusCard());
		GameState.players.get(2).addCard(GameState.cardManager.getRandomCard());
		GameState.players.get(2).addFood(Food.FISH, 5);
		GameState.players.get(2).addBonusCard(GameState.cardManager.getRandomBonusCard()); }
		
		//----------------------------------------------------------------------------------------------------------------------------
		WingspanFrame game = new WingspanFrame("Wingspan");
	}
}
