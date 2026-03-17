package model.pokemon.items;

import model.pokemon.Item;
import model.pokemon.Pokemon;
import model.pokemon.Type;

public class Balloon implements Item {

    private boolean intact = true;

    @Override
    public void onTurnEnd(Pokemon owner) {}

    @Override
    public void onAttackReceived(Pokemon owner, Type attackType) {
        if (intact && attackType != Type.GROUND) {
            intact = false;
            owner.setItem(null);
        }
    }

    public boolean isIntact() { return intact; }

    public boolean grantsImmunity(Type attackType) {
        return intact && attackType == Type.GROUND;
    }

    @Override
    public String getName() { return "Ballon"; }

    @Override
    public String getDescription() { return "Immunité Sol, détruit si le Pokémon est touché"; }
}
