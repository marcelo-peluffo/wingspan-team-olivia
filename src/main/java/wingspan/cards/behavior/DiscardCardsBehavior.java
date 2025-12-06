package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.*;

public class DiscardCardsBehavior implements PowerBehavior{

    PowerBehavior secondBehavior;
    public DiscardCardsBehavior(BehaviorParameters params) {
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        return true;
    }
    
    @Override
    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
}
