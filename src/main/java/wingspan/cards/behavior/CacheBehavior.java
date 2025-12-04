package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.*;

public class CacheBehavior implements PowerBehavior{

    public CacheBehavior(BehaviorParameters params) {

    }

    @Override
    public boolean executePower() {
        GameState.activeCard.addFoodToken(Food.WHEAT); //card with this ability only take wheat
        return true;
    }
    
}
