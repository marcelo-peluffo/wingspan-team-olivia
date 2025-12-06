package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.GameState;

public class LayEggBehavior implements PowerBehavior {
    private int numEggs;
    private boolean onThisBird;
    private NestType nestType;
    PowerBehavior secondBehavior;
    public LayEggBehavior(BehaviorParameters params) {
        this.numEggs = params.numEggs;
        this.onThisBird = params.onThisBird;
        this.nestType = params.nestType;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        // lay egg behavior
        return GameState.activeCard.addEggs(1);
    }

    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
}
