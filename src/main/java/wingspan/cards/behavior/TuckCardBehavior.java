package wingspan.cards.behavior;

import wingspan.cards.*;
import wingspan.cards.behavior.*;
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
        boolean successful = false;

        for (int i = 0; i < numCards; i++)
        {
            if (fromHand) {
                activePlayer.removeCard(activeCard);
            } else {
                CardManager.birdCards.remove(activeCard);
            }  

            successful = activeCard.getTuckedCards().add(activeCard);
        }

        if (secondBehavior != null) {
            secondBehavior.executePower();
        }

        return successful;
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
