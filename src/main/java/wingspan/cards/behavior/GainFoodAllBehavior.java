package wingspan.cards.behavior;
import wingspan.core.*;
import wingspan.enums.Food;

public class GainFoodAllBehavior implements PowerBehavior{
    private Food typeOfFood;
    PowerBehavior secondBehavior;

    public GainFoodAllBehavior(BehaviorParameters params)
    {
        this.typeOfFood = params.typeOfFood;
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }


    @Override
    public boolean executePower() {
        for(Player p: GameState.players)
        {
            p.addFood(typeOfFood, 1); //all cards involving this ability only add 1 food
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
