package wingspan.cards.behavior;

import wingspan.enums.*;

public class EggForFoodBehavior implements PowerBehavior {
    private int numEggs;
    private int numFood;
    private boolean onThisBird;
    private Food typeOfFood;
    PowerBehavior secondBehavior;
    // Constructor 1
    public EggForFoodBehavior(BehaviorParameters params) {
        this.numEggs = params.numEggs;
        this.numFood = params.numFood;
        this.onThisBird = params.onThisBird;
        this.typeOfFood = params.typeOfFood;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        // egg for food behavior
        return true;
    }

    public int getNumEggs() {
        return numEggs;
    }

    public int getNumFood() {
        return numFood;
    }

    public boolean getOnThisBird() {
        return onThisBird;
    }

    public Food getTypeOfFood() {
        return typeOfFood;
    }

    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
}
