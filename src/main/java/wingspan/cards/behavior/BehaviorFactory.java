package wingspan.cards.behavior;

public class BehaviorFactory {
    public static PowerBehavior createBehavior(BehaviorParameters params) {
        if (params == null) return null;
        
        switch (params.type.toUpperCase()) {
            case "TUCK_CARD":
                return new TuckCardBehavior(params);
            case "DRAW_CARD":
                return new DrawCardBehavior(params);
            case "GAIN_FOOD":
                return new GainFoodBehavior(params);
            case "EGG_FOR_FOOD":
                return new EggForFoodBehavior(params);
            case "FOOD_FOR_EGG":
                return new FoodForEggBehavior(params);
            case "LAY_EGG":
                return new LayEggBehavior(params);
            case "PLAY_PREV_BROWN":
                return new PlayPreviousBrownBehavior(params);
            case "ROLL_DICE":
                return new RollDiceBehavior(params);
            case "DISCARD_EGGS":
                return new DiscardEggsBehavior(params);

            default:
                throw new IllegalArgumentException("Unknown behavior type: " + params.type);
        }
    }
}
