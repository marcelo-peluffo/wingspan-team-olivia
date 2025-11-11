package wingspan.cards.behavior;

public class RollDiceBehavior implements PowerBehavior {
    private int numDice;
    private boolean onlyOutsideFeeder;

    public RollDiceBehavior(BehaviorParameters params) {
        this.numDice = params.numDice;
        this.onlyOutsideFeeder = params.onlyOutsideFeeder; 
    }

    @Override
    public boolean executePower() {
        // roll dice behavior
        return true;
    }

    public int getNumDice() {
        return numDice;
    }

    public boolean isOnlyOutsideFeeder() {
        return onlyOutsideFeeder;
    }
}
