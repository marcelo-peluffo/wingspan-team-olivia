package wingspan.core;

import java.io.IOException;
import java.util.*;
import wingspan.food.*;
import wingspan.cards.*;
import wingspan.enums.Food;

public class GameState {
	//these are initialized in WingspanRunner's main method
	public static FoodManager foodManager;
	public static CardManager cardManager;
	public static List<Player> players;
	public static GoalBoard goalBoard;
	public static Player activePlayer; //current turn's player
	public static boolean gameStarted;
	public static boolean isSetup;
	public static int roundNum; // current round number

	//variables used for PowerBehavior logic
	public static ArrayList<Player> abilityPlayers = new ArrayList<Player>(); /* 
		when an ability is activated, this ArrayList will contain all players that are involved in that ability.
		e.g., if the ability is "player with least wetland cards draw a card", this ArrayList would contain all players that have the least amount of wetland cards
		put players in this list based on player number order
	*/
	public static ArrayList<Card> cardsToDraw = new ArrayList<Card>(); //contains the cards a player chose to draw for an ability
	public static ArrayList<Card> cardsToTuck = new ArrayList<Card>(); //contains the cards a player chose to tuck for an ability
	//if multiple players are involved in one of these abilities, put cards in these lists in player number order (eg. player 1's cards in the list come before player 3's)
	//exception: if the ability involves drawing cards, then rotating through each player to take them, put them in clockwise order, starting from the active player

	public static Food foodChoice; //if an ability allows the player to choose any food, it will be stored here.

	public static boolean activePlayerTuckedCard;
	public static Card activeCard; // contains the card whose power is currently being executed
	public static Card selectedCard; // contains the card the user selects for caching food, laying eggs, or tucking cards
	public static boolean choseToCache;

	public static void initialize() {
		// Core managers
		try {
			foodManager = new FoodManager();
			cardManager = new CardManager();
			cardManager.initializeBonusCards();
			goalBoard = new GoalBoard();
			roundNum = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Players
		players = new ArrayList<>();  // empty list, ready to be filled externally

		cardManager.refillVisibleCards();

		// Active player starts as null until game sets it
		activePlayer = null;

		// Ability-related lists
		abilityPlayers.clear();
		cardsToDraw.clear();
		cardsToTuck.clear();

		// Misc. state
		foodChoice = null;
		activePlayerTuckedCard = false;
		activeCard = null;
		selectedCard = null;
		choseToCache = false;

		gameStarted = false;
		roundNum = 1;
	}
	
}
