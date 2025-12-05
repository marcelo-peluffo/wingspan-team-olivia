package wingspan.cards.behavior;
import wingspan.food.*;
import wingspan.core.GameState;
import java.util.*;
import wingspan.enums.Food;
import wingspan.cards.*;

public class WingspanBehavior implements PowerBehavior {
    private int wingSpan;

    public WingspanBehavior(BehaviorParameters params) {
        this.wingSpan = params.wingSpan;
    }

    @Override
    public boolean executePower() {
        //wingspan behavior
        if (GameState.wingspanCard.getBirdInfo().getWingSpan() < wingSpan)
        {
            GameState.activeCard.tuckCard(GameState.wingspanCard);
            return true;
        }
        else
        {
            CardManager.birdCards.add(GameState.wingspanCard);
            return false;
        }
    }
}
