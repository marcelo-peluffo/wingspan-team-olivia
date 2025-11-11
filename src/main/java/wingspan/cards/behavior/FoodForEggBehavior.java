package wingspan.cards.behavior;

import wingspan.enums.*;

public class FoodForEggBehavior implements PowerBehavior {
    private int numFood;
    private Food typeOfFood;
    private int numEggs;

    // Constructor 1
    public FoodForEggBehavior(BehaviorParameters params) {
        this.numFood = params.numFood;
        this.typeOfFood = params.typeOfFood;
        this.numEggs = params.numEggs;
    }

    @Override
    public boolean executePower() {
        // food for egg behavior
        return true;
    }

    public int getNumFood() {
        return numFood;
    }

    public Food gettypeOfFood() {
        return typeOfFood;
    }

    public int getNumEggs() {
        return numEggs;
    }
}
