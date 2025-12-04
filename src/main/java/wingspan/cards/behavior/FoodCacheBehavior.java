package wingspan.cards.behavior;
import wingspan.enums.*;
import wingspan.core.*;

public class FoodCacheBehavior implements PowerBehavior{

    public FoodCacheBehavior(BehaviorParameters params) {
        //this class contains no variables because all cards with this power trade exactly 1 wheat token
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
    
}
