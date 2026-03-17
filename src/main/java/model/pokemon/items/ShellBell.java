package model.pokemon.items;

import model.pokemon.Item;
import model.pokemon.Pokemon;
import model.pokemon.Type;

public class ShellBell implements Item {

    @Override
    public void onTurnEnd(Pokemon owner) {}

    @Override
    public void onAttackReceived(Pokemon owner, Type attackType) {}

    public void onDamageDealt(Pokemon owner, int damage) {
        owner.heal(damage / 8);
    }

    @Override
    public String getName() { return "Sonnette"; }

    @Override
    public String getDescription() { return "Soigne 1/8 des dégâts infligés"; }
}
