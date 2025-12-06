package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.GameState;

public class PlayCardBehavior implements PowerBehavior {
    private int numEggs;
    private boolean onThisBird;
    private NestType nestType;
    PowerBehavior secondBehavior;
    public PlayCardBehavior(BehaviorParameters params) {
        this.numEggs = params.numEggs;
        this.onThisBird = params.onThisBird;
        this.nestType = params.nestType;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        return true;
    }

    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
    @Override
    public BehaviorParameters getBehaviorParams() {
        return null;
    }
}
