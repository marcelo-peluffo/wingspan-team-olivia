package wingspan.cards.behavior;

public class TuckCardBehavior implements PowerBehavior {
    private int numCards;
    private boolean fromHand;
    private PowerBehavior secondBehavior;

    public TuckCardBehavior(BehaviorParameters params) {
        this.numCards = params.numCards;
        this.fromHand = params.fromHand;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        // tuck card behavior
        return true;
    }

    public int getNumCards() {
        return numCards;
    }

    public PowerBehavior getSecondBehavior()
    {
        return secondBehavior;
    }

    public boolean isFromHand() {
        return fromHand;
    }
}
