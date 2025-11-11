package wingspan.cards.behavior;

public class DrawCardBehavior implements PowerBehavior {
    private int numCards;
    private int cardsToTuck;

    public DrawCardBehavior(BehaviorParameters params)
    {
        this.numCards = params.numCards;
        this.cardsToTuck = params.cardsToTuck;
    }

    @Override
    public boolean executePower()
    {
        // logic to draw card and optionally tuck
        return true;
    }

    public int getNumCards() {
        return numCards;
    }

    public int getCardsToTuck() {
        return cardsToTuck;
    }

}
