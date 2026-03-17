package model.pokemon.abilities;

import model.pokemon.Ability;
import model.pokemon.Pokemon;
import model.pokemon.Type;

public class Blaze implements Ability {

    public static final double BOOST = 1.5;

    @Override
    public void onTurnEnd(Pokemon owner) {}

    @Override
    public void onAttackReceived(Pokemon owner, Pokemon attacker) {}

    @Override
    public boolean isImmuneTo(Type attackType) { return false; }

    public boolean isActive(Pokemon owner) {
        return owner.getCurrentHp() <= owner.getBaseHp() / 3.0;
    }

    @Override
    public String getName() { return "Brasier"; }

    @Override
    public String getDescription() { return "Booste les attaques Feu x1.5 si HP < 1/3"; }
}
