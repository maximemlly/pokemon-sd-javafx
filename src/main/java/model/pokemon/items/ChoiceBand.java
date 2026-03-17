package model.pokemon.items;

import model.pokemon.Item;
import model.pokemon.Pokemon;
import model.pokemon.Type;

public class ChoiceBand implements Item {

    public static final double BOOST = 1.5;

    @Override
    public void onTurnEnd(Pokemon owner) {}

    @Override
    public void onAttackReceived(Pokemon owner, Type attackType) {}

    @Override
    public String getName() { return "Bandeau Choix"; }

    @Override
    public String getDescription() { return "Booste l'attaque physique x1.5"; }
}
