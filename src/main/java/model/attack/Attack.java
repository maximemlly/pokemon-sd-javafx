package model.attack;

import model.pokemon.Type;

public class Attack {
    private final String name;
    private final int power;
    private final Type type;
    private final boolean isPhysical;
    private final int pp;
    private int currentPp;
    private final Effect effect;

    public Attack(String name, int power, Type type, boolean isPhysical, int pp, Effect effect) {
        this.name = name;
        this.power = power;
        this.type = type;
        this.isPhysical = isPhysical;
        this.pp = pp;
        this.currentPp = pp;
        this.effect = effect;
    }

    public Attack(String name, int power, Type type, boolean isPhysical, int pp) {
        this(name, power, type, isPhysical, pp, null);
    }

    public boolean hasEffect() {
        return effect != null;
    }

    public void applyEffect(model.pokemon.Pokemon attacker, model.pokemon.Pokemon defender) {
        if (hasEffect()) {
            effect.apply(attacker, defender);
        }
    }

    public boolean usePp() {
        if(currentPp <= 0) return false;
        currentPp--;
        return true;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public Type getType() {
        return type;
    }

    public boolean isPhysical() {
        return isPhysical;
    }

    public int getPp() {
        return pp;
    }

    public int getCurrentPp() {
        return currentPp;
    }

    public Effect getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return name + " [" + type + " | " + (isPhysical ? "Physique" : "Special")
                + " | PWR:" + power + " | PP:" + currentPp + "/" + pp + "]";
    }
}
