package wingspan.cards.behavior;

import wingspan.cards.*;
import wingspan.core.*;

public class TuckCardBehavior implements PowerBehavior {
    private int numCards;
    private boolean fromHand;
    private PowerBehavior secondBehavior;

    public TuckCardBehavior(BehaviorParameters params) {
        this.numCards = params.numCards;
        this.fromHand = params.fromHand;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        // tuck card behavior
        Card activeCard = GameState.activeCard;
        Player activePlayer = GameState.activePlayer;

        if (fromHand) {
            activePlayer.removeCard(activeCard);
        } else {
            CardManager.birdCards.remove(activeCard);
        }

        return activeCard.getTuckedCards().add(activeCard);
    }

    public int getNumCards() {
        return numCards;
    }

    public PowerBehavior getSecondBehavior()
    {
        return secondBehavior;
    }

    public boolean isFromHand() {
        return fromHand;
    }
}
