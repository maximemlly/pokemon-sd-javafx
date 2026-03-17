package model.pokemon.abilities;

import model.pokemon.Ability;
import model.pokemon.Pokemon;
import model.pokemon.Status;
import model.pokemon.Type;

public class Immunity implements Ability {

    @Override
    public void onTurnEnd(Pokemon owner) {}

    @Override
    public void onAttackReceived(Pokemon owner, Pokemon attacker) {
        if (owner.getStatus() == Status.POISON) {
            owner.setStatus(Status.NONE);
        }
    }

    @Override
    public boolean isImmuneTo(Type attackType) { return false; }

    @Override
    public String getName() { return "Vaccin"; }

    @Override
    public String getDescription() { return "Le Pokémon ne peut pas être empoisonné"; }
}
