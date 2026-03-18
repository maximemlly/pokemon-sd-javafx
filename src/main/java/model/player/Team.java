package model.player;

import model.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private final List<Pokemon> pokemons = new ArrayList<>();
    private int activeIndex = 0; // index du Pokémon actif

    public void addPokemon(Pokemon p) {
        if (pokemons.size() < 6) pokemons.add(p);
    }

    public Pokemon getActivePokemon() {
        if (pokemons.isEmpty()) return null;
        return pokemons.get(activeIndex);
    }

    public void setActiveIndex(int index) {
        if (index >= 0 && index < pokemons.size() && !pokemons.get(index).isKO()) {
            activeIndex = index;
        }
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public boolean hasRemainingPokemon() {
        return pokemons.stream().anyMatch(p -> !p.isKO());
    }

    public Pokemon getPokemon(int index) {
        if (index < 0 || index >= pokemons.size()) return null;
        return pokemons.get(index);
    }

    public int size() {
        return pokemons.size();
    }
}