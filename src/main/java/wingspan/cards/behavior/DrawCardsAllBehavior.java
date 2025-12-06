package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.*;

public class DrawCardsAllBehavior implements PowerBehavior {
    PowerBehavior secondBehavior;
    public DrawCardsAllBehavior(BehaviorParameters params) {
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        // all players draw a card behavior
        for(Player p: GameState.players)
        {
            p.addCard(GameState.cardManager.getRandomCard());
        }
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
