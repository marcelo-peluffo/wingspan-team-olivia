package wingspan.cards.behavior;
import wingspan.food.*;
import wingspan.core.GameState;
import java.util.*;
import wingspan.enums.Food;

public class RollDiceBehavior implements PowerBehavior {
    private int numDice;
    private boolean onlyOutsideFeeder;
    private Food targetFood;

    public RollDiceBehavior(BehaviorParameters params) {
        this.numDice = params.numDice;
        this.onlyOutsideFeeder = params.onlyOutsideFeeder; 
        this.targetFood = params.typeOfFood;
    }

    @Override
    public boolean executePower() {
        // roll dice behavior
        if (onlyOutsideFeeder)
        {
            ArrayList<Food> results = new ArrayList<Food>();
            for (FoodDice fd: GameState.foodManager.getUsedDice())
            {
                fd.rerollDice();
                results.add(fd.getFood());
            }
            if (results.contains(targetFood))
            {
                GameState.activeCard.addFoodToken(targetFood);
            }
        }
        return true;
    }

    public int getNumDice() {
        return numDice;
    }

    public boolean isOnlyOutsideFeeder() {
        return onlyOutsideFeeder;
    }
}
