package model.attack.effect;

import model.attack.Effect;
import model.pokemon.Pokemon;

public class DrainEffect implements Effect {
    private final double ratio;

    public DrainEffect(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public void apply(Pokemon attacker, Pokemon defender) {
        int heal = (int)(defender.getLastDamageTaken() * ratio);
        attacker.heal(heal);
    }

    @Override
    public String getDescription() {
        return "";
    }
}
