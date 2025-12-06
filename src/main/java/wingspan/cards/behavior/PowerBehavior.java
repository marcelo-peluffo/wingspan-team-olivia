package wingspan.cards.behavior;

public interface PowerBehavior {
    boolean executePower();
    default String describe() { // does not account for nested powers but know that nested should still exist
        return getClass().getSimpleName();
    }
    
    PowerBehavior getSecondBehavior();
    BehaviorParameters getBehaviorParams();
}