package model.pokemon.abilities;

import model.pokemon.Ability;
import model.pokemon.Pokemon;
import model.pokemon.Type;

public class Levitate implements Ability {

    @Override
    public void onTurnEnd(Pokemon owner) {}

    @Override
    public void onAttackReceived(Pokemon owner, Pokemon attacker) {}

    @Override
    public boolean isImmuneTo(Type attackType) {
        return attackType == Type.GROUND;
    }

    @Override
    public String getName() { return "Lévitation"; }

    @Override
    public String getDescription() { return "Immunité aux attaques de type Sol"; }
}
