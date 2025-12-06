package wingspan.cards.behavior;
import wingspan.core.GameState;
import wingspan.core.Player;
import wingspan.cards.*;
import wingspan.core.*;

public class DrawCardBehavior implements PowerBehavior {
    private int numCards;
    private int cardsToTuck;
    private boolean rotatePlayers;
    private PowerBehavior secondBehavior;

    public DrawCardBehavior(BehaviorParameters params)
    {
        this.numCards = params.numCards;
        this.cardsToTuck = params.cardsToTuck;
        this.rotatePlayers = params.rotatePlayers;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower()
    {
        for(Card c: GameState.chosenCards)
        {
            GameState.activePlayer.addCard(c);
        }
        GameState.chosenCards.clear();
        return true;
    }

    @Override
    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
}
