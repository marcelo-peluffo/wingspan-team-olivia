package wingspan.core;

import java.io.*;
import java.util.*;
import wingspan.WingspanFrame;
import wingspan.cards.DataTable;
import wingspan.enums.*;
import java.awt.Color;
import wingspan.cards.*;


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
		Card testCard = GameState.cardManager.getSpecifiedCard("Acorn Woodpecker");
		GameState.activePlayer.getGameBoard().getForest().add(testCard);
		System.out.println(testCard.getBirdInfo().getBehavior().describe());
		//----------------------------------------------------------------------------------------------------------------------------
		WingspanFrame game = new WingspanFrame("Wingspan");
	}
}
