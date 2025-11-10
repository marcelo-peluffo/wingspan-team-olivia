package wingspan;

import wingspan.cards.*;

public class BehaviorFactoryTest {

    public static void main(String[] args) {
        for (Card c : CardManager.birdCards)
        {
            System.out.println(c.getBirdInfo());
        }
    }
}