package wingspan.cards.behavior;
import wingspan.core.GameState;
import wingspan.core.Player;

public class DrawCardBehavior implements PowerBehavior {
    private int numCards;
    private int cardsToTuck;

    public DrawCardBehavior(BehaviorParameters params)
    {
        this.numCards = params.numCards;
        this.cardsToTuck = params.cardsToTuck;
    }

    @Override
    public boolean executePower()
    {
        int index = 0;
        // logic to draw card and optionally tuck
        for(Player p: GameState.abilityPlayers)
        {
            for(int i=index; i<index + numCards; i++) //for each player that drew cards, loop through and add to their deck the cards they chose
            {
                p.addCard(GameState.cardsToDraw.get(i));
                index++;
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
