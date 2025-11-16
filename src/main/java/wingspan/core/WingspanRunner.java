package wingspan.core;

import java.io.*;
import wingspan.cards.CardManager;
import wingspan.food.FoodManager;
import wingspan.WingspanFrame;


public class WingspanRunner {
	public static void main(String[]args) throws IOException
	{
		GameState.initialize();
		
		for(int i=0; i<4; i++)
		{
			GameState.players.add(new Player());
		}
		

		WingspanFrame game = new WingspanFrame("Wingspan");
	}
}
