package wingspan.cards.behavior;

import wingspan.enums.*;

import wingspan.core.GameState;

public class MoveCardBehavior implements PowerBehavior {

    PowerBehavior secondBehavior;
    public MoveCardBehavior(BehaviorParameters params)
    {
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower()
    {
        GameState.activePlayer.getGameBoard().removeCard(GameState.activeCardHabitat);
        GameState.activePlayer.getGameBoard().addCard(GameState.activeCard, GameState.chosenHabitat);
        return true;
    }

    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
}
