package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.*;

public class BonusCardBehavior implements PowerBehavior{

    PowerBehavior secondBehavior;

    public BonusCardBehavior(BehaviorParameters params) {
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        GameState.activePlayer.addBonusCard(GameState.selectedBonusCard);
        return true;
    }

    @Override
    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
    
}
