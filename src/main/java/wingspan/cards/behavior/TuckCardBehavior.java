package wingspan.cards.behavior;
import wingspan.core.GameState;
import wingspan.cards.Card;

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
        for(Card c: GameState.cardsToTuck)
        {
            GameState.activeCard.tuckCard(c);
        }
        GameState.cardsToTuck.clear();
        if (this.secondBehavior != null)
        {
            secondBehavior.executePower();
        }
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
