package wingspan.cards.behavior;
import wingspan.enums.NestType;
import wingspan.core.GameState;

public class DiscardEggsBehavior implements PowerBehavior {
    // fortunately, every ability involving this only discards 1 egg, making things less complicated
    private boolean onThisBird;
    private NestType nestType;
    private PowerBehavior secondPowerBehavior;

    public DiscardEggsBehavior(BehaviorParameters params) {
        this.onThisBird = params.onThisBird;
        this.nestType = params.nestType;
        if (params.secondBehavior != null) {
            this.secondPowerBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        // lay egg behavior
        return true;
    }

    @Override
    public PowerBehavior getSecondBehavior() {
        return secondPowerBehavior;
    }

    public boolean isOnThisBird() {
        return onThisBird;
    }

    public NestType getNestType() {
        return nestType;
    }
    @Override
    public BehaviorParameters getBehaviorParams() {
        return null;
    }
}
