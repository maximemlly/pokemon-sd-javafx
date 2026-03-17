package model.attack.effect;

import model.attack.Effect;
import model.pokemon.Pokemon;
import model.pokemon.Status;

import java.util.Random;

public class BurnEffect implements Effect {
    private final double chance;

    public BurnEffect(double chance) {
        this.chance = chance;
    }


    @Override
    public void apply(Pokemon attacker, Pokemon defender) {
        if(defender.getStatus() == Status.NONE && new Random().nextDouble() < chance) {
            defender.setStatus(Status.BURN);
        }
    }

    @Override
    public String getDescription() {
        return (int)(chance * 100) + "% chance of burning";
    }
}
