package wingspan.cards.behavior;
import wingspan.food.*;
import wingspan.core.GameState;
import java.util.*;
import wingspan.enums.Food;

public class RollDiceBehavior implements PowerBehavior {
    private Food targetFood;
    PowerBehavior secondBehavior;
    public RollDiceBehavior(BehaviorParameters params) {
        this.targetFood = params.targetFood;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        // roll dice behavior
        for(FoodDice fd: GameState.foodManager.getUsedDice())
        {
            fd.rerollDice();
        }
        for(FoodDice fd: GameState.foodManager.getUsedDice())
        {
            if (fd.getFood() == targetFood)
            {
                GameState.activeCard.addFoodToken(targetFood);
                return true;
            }
        }
        return false;
    }

    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
}
