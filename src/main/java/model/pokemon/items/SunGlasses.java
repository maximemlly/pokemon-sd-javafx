package model.pokemon.items;

import model.pokemon.Item;
import model.pokemon.Pokemon;
import model.pokemon.Type;

public class SunGlasses implements Item {

    public static final double BOOST = 1.2;

    @Override
    public void onTurnEnd(Pokemon owner) {}

    @Override
    public void onAttackReceived(Pokemon owner, Type attackType) {}

    public double getBoostFor(Type attackType) {
        return attackType == Type.DARK ? BOOST : 1.0;
    }

    @Override
    public String getName() { return "Lunettes de soleil"; }

    @Override
    public String getDescription() { return "Booste les attaques Dark x1.2"; }
}
