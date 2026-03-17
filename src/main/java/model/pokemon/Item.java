package model.pokemon;

public interface Item {
    void onTurnEnd(Pokemon owner);
    void onAttackReceived(Pokemon owner, Type attackType);
    String getName();
    String getDescription();
}
