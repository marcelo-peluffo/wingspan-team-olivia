package wingspan.cards.behavior;
import wingspan.core.GameState;
import wingspan.core.Player;

public class DrawCardBehavior implements PowerBehavior {
    private int numCards;
    private int cardsToTuck;
    private boolean rotatePlayers;

    public DrawCardBehavior(BehaviorParameters params)
    {
        this.numCards = params.numCards;
        this.cardsToTuck = params.cardsToTuck;
        this.rotatePlayers = params.rotatePlayers;
    }

    @Override
    public boolean executePower()
    {
        int index = 0;
        // logic to draw card and optionally tuck
        if (!rotatePlayers)
        {
            for(Player p: GameState.abilityPlayers)
            {
                for(int i=index; i<index + numCards; i++) //for each player that drew cards, loop through and add to their deck the cards they chose
                {
                    p.addCard(GameState.cardsToDraw.get(i));
                    index++;
                }
            }
        }
        else
        {
            for(int i=index; i<GameState.players.size(); i++)
            {
                if (GameState.players.get(i).equals(GameState.activePlayer)) //set the index equal to the active player
                {
                    index = i;
                    break;
                }
            }
            for(int i=0; i<numCards; i++)
            {
                GameState.players.get(index).addCard(GameState.cardsToDraw.get(i)); //starting from the active player, rotate through all players clockwise to distribute cards
                index++;
                if (index == GameState.players.size())
                    index = 0;
            }
        }
        GameState.cardsToDraw.clear();
        return true;
    }

    public int getNumCards() {
        return numCards;
    }

    public int getCardsToTuck() {
        return cardsToTuck;
    }

}
