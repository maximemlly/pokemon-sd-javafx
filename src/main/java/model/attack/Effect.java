package model.attack;

import model.pokemon.Pokemon;

public interface Effect {

    void apply(Pokemon attacker, Pokemon defender);
    String getDescription();
}
