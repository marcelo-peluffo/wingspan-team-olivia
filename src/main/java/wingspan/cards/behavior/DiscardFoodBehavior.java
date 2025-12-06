package wingspan.cards.behavior;

import wingspan.enums.*;

import java.util.*;

import wingspan.core.GameState;

public class DiscardFoodBehavior implements PowerBehavior {
    private Food typeOfFood;
    PowerBehavior secondBehavior;

    public DiscardFoodBehavior(BehaviorParameters params)
    {
        this.typeOfFood = params.typeOfFood;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower()
    {
        if (GameState.activePlayer.getFoodInventory().get(typeOfFood) == 0)
        {
            return false;
        }
        GameState.activePlayer.removeFood(typeOfFood, 1);
        return true;
    }

    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
}
