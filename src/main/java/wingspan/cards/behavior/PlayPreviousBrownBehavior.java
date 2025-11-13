package wingspan.cards.behavior;

import java.util.List;

import wingspan.cards.Card;
import wingspan.core.GameState;
import wingspan.core.Player;
import wingspan.enums.Color;

public class PlayPreviousBrownBehavior implements PowerBehavior {
    private int numPowers;
    private boolean activateAll;

    public PlayPreviousBrownBehavior(BehaviorParameters params) {
        this.numPowers = params.numPowers;
        this.activateAll = params.activateAll;
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
            if (currentCard.getBirdInfo().getPowerColor() == Color.BROWN)
            {
                currentCard.getBirdInfo().getBehavior().executePower();
            }
            
        }

        return true;
    }

    public int getNumPowers() {
        return numPowers;
    }

    public boolean activateAll() {
        return activateAll;
    }
}
