package wingspan.cards.behavior;

import java.util.List;

import wingspan.cards.Card;
import wingspan.core.GameState;
import wingspan.core.Player;
import wingspan.enums.PowerColor;

public class PlayPreviousBrownBehavior implements PowerBehavior {
    private int numPowers;
    private boolean activateAll;
    PowerBehavior secondBehavior;
    public PlayPreviousBrownBehavior(BehaviorParameters params) {
        this.numPowers = params.numPowers;
        this.activateAll = params.activateAll;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }


    @Override
    public boolean executePower() 
    {
        // play previous brown power behavior
        Player activePlayer = GameState.activePlayer;
        List<Card> activeHabitatCards = activePlayer.getGameBoard().getActiveHabitat();
        Card activeCard = GameState.activeCard;

        for (int i = activeHabitatCards.indexOf(activeCard) + 1; i < activeHabitatCards.size(); i++)
        {
            Card currentCard = activeHabitatCards.get(i);
            if (currentCard.getBirdInfo().getPowerColor() == PowerColor.BROWN)
            {
                currentCard.getBirdInfo().getBehavior().executePower();
                return true;
            }
            
        }

        return false;
    }

    public int getNumPowers() {
        return numPowers;
    }

    public boolean activateAll() {
        return activateAll;
    }

    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
}
