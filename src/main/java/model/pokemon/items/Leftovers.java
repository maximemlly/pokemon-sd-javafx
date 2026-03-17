package model.pokemon.items;

import model.pokemon.Item;
import model.pokemon.Pokemon;
import model.pokemon.Type;

public class Leftovers implements Item {

    @Override
    public void onTurnEnd(Pokemon owner) {
        owner.heal(owner.getBaseHp() / 16);
    }

    @Override
    public void onAttackReceived(Pokemon owner, Type attackType) {}

    @Override
    public String getName() { return "Restes"; }

    @Override
    public String getDescription() { return "Restaure 1/16 des HP à chaque fin de tour"; }
}
