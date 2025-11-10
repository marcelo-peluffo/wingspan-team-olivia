package test.wingspan;

import wingspan.cards.behavior.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BehaviorFactoryTest {

    public static void main(String[] args) {
        try {
            // ✅ Example 1: Simple behavior
            String jsonSimple = """
                {
                    "type": "TUCK_CARD",
                    "numCards": 2,
                    "fromHand": true
                }
            """;

            BehaviorParameters params1 = new ObjectMapper().readValue(jsonSimple, BehaviorParameters.class);
            PowerBehavior behavior1 = BehaviorFactory.createBehavior(params1);
            System.out.println("✅ Created behavior: " + behavior1.getClass().getSimpleName());
            System.out.println("   numCards=" + ((TuckCardBehavior) behavior1).getNumCards());
            System.out.println("   fromHand=" + ((TuckCardBehavior) behavior1).isFromHand());

            // ✅ Example 2: Nested behavior
            String jsonNested = """
                {
                    "type": "TUCK_CARD",
                    "numCards": 1,
                    "fromHand": false,
                    "secondBehavior": {
                        "type": "DRAW_CARD",
                        "numCards": 1
                    }
                }
            """;

            BehaviorParameters params2 = new ObjectMapper().readValue(jsonNested, BehaviorParameters.class);
            PowerBehavior behavior2 = BehaviorFactory.createBehavior(params2);
            System.out.println("✅ Created nested behavior: " + behavior2.getClass().getSimpleName());
            System.out.println("   Second behavior: " +
                    ((TuckCardBehavior) behavior2).getSecondBehavior().getClass().getSimpleName());

            // ✅ Example 3: Invalid behavior type
            String jsonInvalid = """
                { "type": "SING_SONG" }
            """;

            try {
                BehaviorParameters params3 = new ObjectMapper().readValue(jsonInvalid, BehaviorParameters.class);
                BehaviorFactory.createBehavior(params3);
            } catch (Exception e) {
                System.out.println("✅ Caught expected error for invalid type: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}