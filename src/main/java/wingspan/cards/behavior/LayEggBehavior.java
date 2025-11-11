package wingspan.cards.behavior;
import wingspan.enums.NestType;
import wingspan.core.GameState;

public class LayEggBehavior implements PowerBehavior {
    private int numEggs;
    private boolean onThisBird;
    private NestType nestType;

    public LayEggBehavior(BehaviorParameters params) {
        this.numEggs = params.numEggs;
        this.onThisBird = params.onThisBird;
        this.nestType = params.nestType;
    }

    @Override
    public boolean executePower() {
        // lay egg behavior
        if (GameState.selectedCard.getBirdInfo().getNestType() != nestType || (!onThisBird && GameState.selectedCard.equals(GameState.activeCard)))
        {
            return false;
        }
        else
        {
            if (!GameState.selectedCard.addEggs(numEggs))
            {
                return false;
            }
        }
        return true;
    }

    public int getNumEggs() {
        return numEggs;
    }

    public boolean isOnThisBird() {
        return onThisBird;
    }

    public NestType getNestType() {
        return nestType;
    }
}
