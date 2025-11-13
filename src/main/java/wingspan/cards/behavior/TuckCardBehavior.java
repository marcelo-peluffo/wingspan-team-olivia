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
    public boolean executePower() { // user chooses which cards to tuck and program waits on async decisions
        // tuck card behavior
        Card activeCard;
        Player activePlayer = GameState.activePlayer;
        boolean successful = false;

        for (int i = 0; i < numCards; i++)
        {
            activeCard = GameState.activeCard; // update to later to reflect user choice (activeCard = user.CardSelection) as an async operation
            if (activeCard == null) continue;
            
            if (fromHand) {
                activePlayer.removeCard(activeCard);
            } else {
                CardManager.birdCards.remove(activeCard); // same here, except from deck
            }  

            successful |= activeCard.getTuckedCards().add(activeCard); // was it successful at any point?
        }

        if (secondBehavior != null) {
            successful |= secondBehavior.executePower(); // same here.
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
