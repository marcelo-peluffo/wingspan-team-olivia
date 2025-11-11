package wingspan.core;

import java.util.*;
import wingspan.food.*;
import wingspan.cards.*;

public class GameState {
	//these are initialized in WingspanRunner's main method
	public static FoodManager foodManager;
	public static CardManager cardManager;
	public static ArrayList<Player> players;
	public static GoalBoard goalBoard;
	public static Player activePlayer;

	//variables containing players with the least cards in the respective habitats - used for PowerBehavior logic
	public static ArrayList<Player> leastGrasslands;
	public static ArrayList<Player> leastWetlands;
	public static ArrayList<Player> leastForest;

	public static boolean activePlayerTuckedCard;
	public static Card activeCard; // contains the card whose power is currently being executed
	public static Card selectedCard; // contains the card the user selects for caching food, laying eggs, or tucking cards
	public static boolean choseToCache;

}
