package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.GameState;

public class LayEggAnyBehavior implements PowerBehavior {
    private boolean onThisBird;
    private NestType nestType;
    PowerBehavior secondBehavior;
    BehaviorParameters behaviorParams;
    public LayEggAnyBehavior(BehaviorParameters params) {
        this.onThisBird = params.onThisBird;
        this.nestType = params.nestType;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
        behaviorParams = params;
    }

    @Override
    public boolean executePower() {
        // lay egg behavior
        return GameState.activeCard.addEggs(1);
    }

    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
    @Override
    public BehaviorParameters getBehaviorParams() {
        return behaviorParams;
    }
}
