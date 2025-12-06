package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.*;

public class CacheBehavior implements PowerBehavior{

    PowerBehavior secondBehavior;
    public CacheBehavior(BehaviorParameters params) {
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        GameState.activeCard.addFoodToken(Food.WHEAT); //card with this ability only take wheat
        return true;
    }
    
    @Override
    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }

    @Override
    public BehaviorParameters getBehaviorParams() {
        return null;
    }
}
