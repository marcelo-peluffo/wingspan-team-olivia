package wingspan.core;

import java.util.*;
import wingspan.food.*;
import wingspan.cards.*;
import wingspan.enums.Food;

public class GameState {
	//these are initialized in WingspanRunner's main method
	public static FoodManager foodManager;
	public static CardManager cardManager;
	public static ArrayList<Player> players;
	public static GoalBoard goalBoard;
	public static Player activePlayer; //current turn's player

	//variables used for PowerBehavior logic
	public static ArrayList<Player> abilityPlayers = new ArrayList<Player>(); /* 
		when an ability is activated, this ArrayList will contain all players that are involved in that ability.
		e.g., if the ability is "player with least wetland cards draw a card", this ArrayList would contain all players that have the least amount of wetland cards
		put players in this list based on player number order
	*/
	public static ArrayList<Card> cardsToDraw = new ArrayList<Card>(); //contains the cards a player chose to draw for an ability
	public static ArrayList<Card> cardsToTuck = new ArrayList<Card>(); //contains the cards a player chose to tuck for an ability
	//if multiple players are involved in one of these abilities, put cards in these lists in player number order (eg. player 1's cards in the list come before player 3's)

	public static Food foodChoice; //if an ability allows the player to choose any food, it will be stored here.

	public static boolean activePlayerTuckedCard;
	public static Card activeCard; // contains the card whose power is currently being executed
	public static Card selectedCard; // contains the card the user selects for caching food, laying eggs, or tucking cards
	public static boolean choseToCache;

}
