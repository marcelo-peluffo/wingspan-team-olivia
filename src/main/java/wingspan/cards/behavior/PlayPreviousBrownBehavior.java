package wingspan.cards.behavior;

public class PlayPreviousBrownBehavior implements PowerBehavior {
    private int numPowers;
    private boolean activateAll;

    public PlayPreviousBrownBehavior(BehaviorParameters params) {
        this.numPowers = params.numPowers;
        this.activateAll = params.activateAll;
    }


    @Override
    public boolean executePower() {
        // play previous brown power behavior
        return true;
    }

    public int getNumPowers() {
        return numPowers;
    }

    public boolean activateAll() {
        return activateAll;
    }
}
