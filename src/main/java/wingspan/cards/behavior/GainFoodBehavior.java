package wingspan.cards.behavior;

import wingspan.enums.*;

import java.util.*;

import wingspan.core.GameState;

public class GainFoodBehavior implements PowerBehavior {
    private Food typeOfFood;
    private int numFood;
    PowerBehavior secondBehavior;
    BehaviorParameters params;

    public GainFoodBehavior(BehaviorParameters params)
    {
        this.typeOfFood = params.typeOfFood;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
        this.numFood = 1;
        this.params = params;
    }

    @Override
    public boolean executePower()
    {
        if (GameState.activeCard.getBirdInfo().getPowerColor() == PowerColor.WHITE)
        {
            GameState.activePlayer.addFood(typeOfFood, params.numFood);
        }
        else
        {
            GameState.activePlayer.addFood(typeOfFood, 1);
        }
        return true;
    }
    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
    @Override
    public BehaviorParameters getBehaviorParams() {
        return params;
    }
}
