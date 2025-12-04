package wingspan.cards.behavior;
import wingspan.core.*;
import wingspan.enums.Food;

public class GainFoodAllBehavior implements PowerBehavior{
    private Food typeOfFood;


    public GainFoodAllBehavior(BehaviorParameters params)
    {
        this.typeOfFood = params.typeOfFood;
    }


    @Override
    public boolean executePower() {
        for(Player p: GameState.players)
        {
            p.addFood(typeOfFood, 1); //all cards involving this ability only add 1 food
        }
        return true;
    }
}
