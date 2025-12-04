package wingspan.cards.behavior;

import wingspan.enums.*;

import java.util.*;

import wingspan.core.GameState;

public class GainFoodBehavior implements PowerBehavior {
    private Food typeOfFood;


    public GainFoodBehavior(BehaviorParameters params)
    {
        this.typeOfFood = params.typeOfFood;
    }

    @Override
    public boolean executePower()
    {
        GameState.activePlayer.addFood(typeOfFood, 1);
        return true;
    }
}
