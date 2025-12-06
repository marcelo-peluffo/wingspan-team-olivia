package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.*;

public class FoodCacheBehavior implements PowerBehavior{
    PowerBehavior secondBehavior;
    public FoodCacheBehavior(BehaviorParameters params) {
        if (params.secondBehavior != null) {
            this.secondBehavior = BehaviorFactory.createBehavior(params.secondBehavior);
        }
    }

    @Override
    public boolean executePower() {
        if (!GameState.foodManager.hasFood(Food.WHEAT) && !GameState.foodManager.hasFood(Food.ANY))
        {
            return false;
        }
        if (GameState.choseToCache)
        {
            GameState.activeCard.addFoodToken(Food.WHEAT);
        }
        else
        {
            GameState.activePlayer.addFood(Food.WHEAT, 1);
        }
        for(int i=0; i<GameState.foodManager.getBirdFeeder().size(); i++)
        {
            if (GameState.foodManager.seeDie(i).getFood() == Food.WHEAT || GameState.foodManager.seeDie(i).getFood() == Food.ANY)
            {
                GameState.foodManager.getDie(i);
                break;
            }
        }
        return true;
        
    }

    public PowerBehavior getSecondBehavior() {
        return secondBehavior;
    }
    
}
