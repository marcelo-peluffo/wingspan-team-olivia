package wingspan.cards.behavior;

import wingspan.enums.*;

import java.util.*;

import wingspan.core.GameState;

public class GainFoodBehavior implements PowerBehavior {
    private int numFood;
    private Food typeOfFood;
    private boolean isCacheable;
    private boolean isFromFeeder;


    public GainFoodBehavior(BehaviorParameters params)
    {
        this.numFood = params.numFood;
        this.typeOfFood = params.typeOfFood;
        this.isCacheable = params.isCacheable;
        this.isFromFeeder = params.isFromFeeder;
    }

    @Override
    public boolean executePower()
    {
        Map<Food, Integer> playerFoodInventory = GameState.activePlayer.getFoodInventory();
        playerFoodInventory.put(typeOfFood, playerFoodInventory.get(typeOfFood) + numFood);
        if (isCacheable)
        {
            if (GameState.choseToCache)
            {
                GameState.activeCard.addFoodToken(typeOfFood); //all the abilities that involve caching only allow caching of one food token
                playerFoodInventory.put(typeOfFood, playerFoodInventory.get(typeOfFood) - 1);
            }
        }
        return true;
    }

    public int getNumFood() 
    {
        return numFood;
    }

    public Food getTypeOfFood() 
    {
        return typeOfFood;
    }

    public boolean isCacheable() {
        return isCacheable;
    }

    public boolean isFromFeeder() {
        return isFromFeeder;
    }
}
