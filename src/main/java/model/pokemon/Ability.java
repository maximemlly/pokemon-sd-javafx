package model.pokemon;

public interface Ability {
    void onTurnEnd(Pokemon owner);
    void onAttackReceived(Pokemon owner, Pokemon attacker);
    boolean isImmuneTo(Type attackType);
    String getName();
    String getDescription();
}
