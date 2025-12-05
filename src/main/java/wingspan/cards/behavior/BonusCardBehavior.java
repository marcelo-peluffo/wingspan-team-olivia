package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.*;

public class BonusCardBehavior implements PowerBehavior{

    public BonusCardBehavior(BehaviorParameters params) {

    }

    @Override
    public boolean executePower() {
        GameState.activePlayer.addBonusCard(GameState.selectedBonusCard);
        return true;
    }
    
}
