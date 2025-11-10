package wingspan;

import wingspan.cards.*;

public class BehaviorFactoryTest {
    public static void main(String[] args) {
        DataTable.initialize();
        for (BirdInfo birdInfo : CardManager.birdInfos)
        {
            System.out.println(birdInfo);
        }
    }
}