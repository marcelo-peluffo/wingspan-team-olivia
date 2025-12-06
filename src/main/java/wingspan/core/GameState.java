package wingspan.core;

import java.io.IOException;
import java.util.*;
import wingspan.food.*;
import wingspan.utils.Pair;
import wingspan.cards.*;
import wingspan.enums.Food;
import java.awt.Color;
import wingspan.utils.Pair;
import wingspan.cards.bonusCards.*;
import wingspan.enums.*;

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
	public static Card activeCard; // contains the card whose power is currently being executed
	public static ArrayList<Player> abilityPlayers = new ArrayList<Player>();
	public static ArrayList<Card> cardsToDraw = new ArrayList<Card>();
	public static ArrayList<Card> cardsToTuck = new ArrayList<Card>();

	public static Food foodChoice; 

	public static boolean activePlayerTuckedCard;
	public static Card selectedCard;
	public static boolean choseToCache; //Used for: FoodCacheBehavior
	public static BonusCard selectedBonusCard; //Used for :BonusCardBehavior
	public static Card wingspanCard; //Used for: WingspanBehavior
	public static Habitat chosenHabitat; //Used for: MoveCardBehavior
	public static ArrayList<Card> chosenCards;
	public static Habitat activeCardHabitat;

	//variables used for paint method
	public static HashMap<Player, Color> actionCubeColors;
	public static HashMap<Card, Pair> cardPositions;
	public static ArrayList<HashMap<Player, Pair>> goalBoardPositions; // stores the positions of the player's action cubes on the goalboard so they can be redrawn for future rounds

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
		chosenCards = new ArrayList<>();

		//action cube colors

		// Misc. state
		foodChoice = null;
		activePlayerTuckedCard = false;
		activeCard = null;
		selectedCard = null;
		choseToCache = false;

		gameStarted = false;
		roundNum = 1;

		goalBoardPositions = new ArrayList<>();
	}
	
}
