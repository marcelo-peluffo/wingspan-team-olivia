package wingspan.cards.behavior;

public class LayEggBehavior implements PowerBehavior {
    private int numEggs;
    private boolean onThisBird;
    private String nestType;

    public LayEggBehavior(BehaviorParameters params) {
        this.numEggs = params.numEggs;
        this.onThisBird = params.onThisBird;
        this.nestType = params.nestType;
    }

    @Override
    public boolean executePower() {
        // lay egg behavior
        return true;
    }

    public int getNumEggs() {
        return numEggs;
    }

    public boolean isOnThisBird() {
        return onThisBird;
    }

    public String getNestType() {
        return nestType;
    }
}
