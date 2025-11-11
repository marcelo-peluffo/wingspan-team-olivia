package wingspan.core;

import java.util.*;
import wingspan.food.*;
import wingspan.cards.*;

public class GameState {
	//these are initialized in WingspanRunner's main method
	public static FoodManager foodManager;
	public static CardManager cardManager;
	public static List<Player> players;
	public static GoalBoard goalBoard;
	public static Player activePlayer;

	//variables containing players with the least cards in the respective habitats - used for PowerBehavior logic
	public static Player leastGrasslands;
	public static Player leastWetlands;
	public static Player leastForest;

	public static boolean activePlayerTuckedCard;
	public static Card activeCard;
}
