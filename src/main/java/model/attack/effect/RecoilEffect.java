package model.attack.effect;

import model.attack.Effect;
import model.pokemon.Pokemon;

public class RecoilEffect implements Effect {
    private final double ratio;

    public RecoilEffect(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public void apply(Pokemon attacker, Pokemon defender) {
        int recoil = (int)(defender.getLastDamageTaken() * ratio);
        attacker.takeDamage(recoil);
    }

    @Override
    public String getDescription() {
        return (int)(ratio * 100) + "% damages taken";
    }
}
