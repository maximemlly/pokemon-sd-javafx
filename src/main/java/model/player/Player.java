package model.player;

import model.pokemon.Pokemon;

public class Player {

    public boolean hasRemainingPokemon() {
        for (int i = 0; i < 6; i++) {
            Pokemon p = getPokemon(i);
            if (p != null && !p.isKO()) {
                return true;
            }
        }
        return false;
    }
}
