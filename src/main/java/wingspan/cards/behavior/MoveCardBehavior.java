package wingspan.cards.behavior;

import wingspan.enums.*;

import wingspan.core.GameState;

public class MoveCardBehavior implements PowerBehavior {


    public MoveCardBehavior(BehaviorParameters params)
    {
        
    }

    @Override
    public boolean executePower()
    {
        GameState.activePlayer.getGameBoard().removeCard(GameState.activeCardHabitat);
        GameState.activePlayer.getGameBoard().addCard(GameState.activeCard, GameState.chosenHabitat);
        return true;
    }
}
