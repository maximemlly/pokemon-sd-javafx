package model.pokemon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Type {
    NORMAL, FIRE, WATER, GRASS, ELECTRIC, ICE,
    FIGHTING, POISON, GROUND, FLYING, PSYCHIC,
    BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY;


    private static final Map<Type, Map<Type, Double>> table = new HashMap<>();

    static {
        // Forces et faiblesse du type normal
        setMultiplier(NORMAL, ROCK, 0.5);
        setMultiplier(NORMAL, GHOST, 0.0);
        setMultiplier(NORMAL, STEEL, 0.5);

        // du type feu
        setMultiplier(FIRE, FIRE, 0.5);
        setMultiplier(FIRE, WATER, 0.5);
        setMultiplier(FIRE, GRASS, 2.0);
        setMultiplier(FIRE, ICE, 2.0);
        setMultiplier(FIRE, BUG, 2.0);
        setMultiplier(FIRE, ROCK, 0.5);
        setMultiplier(FIRE, DRAGON, 0.5);
        setMultiplier(FIRE, STEEL, 2.0);


        setMultiplier(WATER, FIRE, 2.0);
        setMultiplier(WATER, WATER, 0.5);
        setMultiplier(WATER, GRASS, 0.5);
        setMultiplier(WATER, GROUND, 2.0);
        setMultiplier(WATER, ROCK, 2.0);
        setMultiplier(WATER, DRAGON, 0.5);


        setMultiplier(GRASS, FIRE, 0.5);
        setMultiplier(GRASS, WATER, 2.0);
        setMultiplier(GRASS, GRASS, 0.5);
        setMultiplier(GRASS, POISON, 0.5);
        setMultiplier(GRASS, GROUND, 2.0);
        setMultiplier(GRASS, FLYING, 0.5);
        setMultiplier(GRASS, BUG, 0.5);
        setMultiplier(GRASS, ROCK, 2.0);
        setMultiplier(GRASS, DRAGON, 0.5);
        setMultiplier(GRASS, STEEL, 0.5);


        setMultiplier(ELECTRIC, WATER, 2.0);
        setMultiplier(ELECTRIC, GRASS, 0.5);
        setMultiplier(ELECTRIC, ELECTRIC, 0.5);
        setMultiplier(ELECTRIC, GROUND, 0.0);
        setMultiplier(ELECTRIC, FLYING, 2.0);
        setMultiplier(ELECTRIC, DRAGON, 0.5);


        setMultiplier(ICE, FIRE, 0.5);
        setMultiplier(ICE, WATER, 0.5);
        setMultiplier(ICE, GRASS, 2.0);
        setMultiplier(ICE, ICE, 0.5);
        setMultiplier(ICE, GROUND, 2.0);
        setMultiplier(ICE, FLYING, 2.0);
        setMultiplier(ICE, DRAGON, 2.0);
        setMultiplier(ICE, STEEL, 0.5);


        setMultiplier(FIGHTING, NORMAL, 2.0);
        setMultiplier(FIGHTING, ICE, 2.0);
        setMultiplier(FIGHTING, POISON, 0.5);
        setMultiplier(FIGHTING, FLYING, 0.5);
        setMultiplier(FIGHTING, PSYCHIC, 0.5);
        setMultiplier(FIGHTING, BUG, 0.5);
        setMultiplier(FIGHTING, ROCK, 2.0);
        setMultiplier(FIGHTING, GHOST, 0.0);
        setMultiplier(FIGHTING, DARK, 2.0);
        setMultiplier(FIGHTING, STEEL, 2.0);
        setMultiplier(FIGHTING, FAIRY, 0.5);


        setMultiplier(POISON, GRASS, 2.0);
        setMultiplier(POISON, POISON, 0.5);
        setMultiplier(POISON, GROUND, 0.5);
        setMultiplier(POISON, ROCK, 0.5);
        setMultiplier(POISON, GHOST, 0.5);
        setMultiplier(POISON, STEEL, 0.0);
        setMultiplier(POISON, FAIRY, 2.0);


        setMultiplier(GROUND, FIRE, 2.0);
        setMultiplier(GROUND, GRASS, 0.5);
        setMultiplier(GROUND, ELECTRIC, 2.0);
        setMultiplier(GROUND, POISON, 2.0);
        setMultiplier(GROUND, FLYING, 0.0);
        setMultiplier(GROUND, BUG, 0.5);
        setMultiplier(GROUND, ROCK, 2.0);
        setMultiplier(GROUND, STEEL, 2.0);


        setMultiplier(FLYING, ELECTRIC, 0.5);
        setMultiplier(FLYING, GRASS, 2.0);
        setMultiplier(FLYING, FIGHTING, 2.0);
        setMultiplier(FLYING, BUG, 2.0);
        setMultiplier(FLYING, ROCK, 0.5);
        setMultiplier(FLYING, STEEL, 0.5);


        setMultiplier(PSYCHIC, FIGHTING, 2.0);
        setMultiplier(PSYCHIC, POISON, 2.0);
        setMultiplier(PSYCHIC, PSYCHIC, 0.5);
        setMultiplier(PSYCHIC, DARK, 0.0);
        setMultiplier(PSYCHIC, STEEL, 0.5);


        setMultiplier(BUG, FIRE, 0.5);
        setMultiplier(BUG, GRASS, 2.0);
        setMultiplier(BUG, FIGHTING, 0.5);
        setMultiplier(BUG, POISON, 0.5);
        setMultiplier(BUG, FLYING, 0.5);
        setMultiplier(BUG, PSYCHIC, 2.0);
        setMultiplier(BUG, GHOST, 0.5);
        setMultiplier(BUG, DARK, 2.0);
        setMultiplier(BUG, STEEL, 0.5);
        setMultiplier(BUG, FAIRY, 0.5);


        setMultiplier(ROCK, FIRE, 2.0);
        setMultiplier(ROCK, ICE, 2.0);
        setMultiplier(ROCK, FIGHTING, 0.5);
        setMultiplier(ROCK, GROUND, 0.5);
        setMultiplier(ROCK, FLYING, 2.0);
        setMultiplier(ROCK, BUG, 2.0);
        setMultiplier(ROCK, STEEL, 0.5);


        setMultiplier(GHOST, NORMAL, 0.0);
        setMultiplier(GHOST, PSYCHIC, 2.0);
        setMultiplier(GHOST, GHOST, 2.0);
        setMultiplier(GHOST, DARK, 0.5);


        setMultiplier(DRAGON, DRAGON, 2.0);
        setMultiplier(DRAGON, STEEL, 0.5);
        setMultiplier(DRAGON, FAIRY, 0.0);


        setMultiplier(DARK, FIGHTING, 0.5);
        setMultiplier(DARK, PSYCHIC, 2.0);
        setMultiplier(DARK, GHOST, 2.0);
        setMultiplier(DARK, DARK, 0.5);
        setMultiplier(DARK, FAIRY, 0.5);


        setMultiplier(STEEL, FIRE, 0.5);
        setMultiplier(STEEL, WATER, 0.5);
        setMultiplier(STEEL, ELECTRIC, 0.5);
        setMultiplier(STEEL, ICE, 2.0);
        setMultiplier(STEEL, ROCK, 2.0);
        setMultiplier(STEEL, STEEL, 0.5);
        setMultiplier(STEEL, FAIRY, 2.0);


        setMultiplier(FAIRY, FIRE, 0.5);
        setMultiplier(FAIRY, FIGHTING, 2.0);
        setMultiplier(FAIRY, POISON, 0.5);
        setMultiplier(FAIRY, DRAGON, 2.0);
        setMultiplier(FAIRY, DARK, 2.0);
        setMultiplier(FAIRY, STEEL, 0.5);

    }

    private static void setMultiplier(Type attacker, Type defender, double multiplier) {
        table.computeIfAbsent(attacker, k -> new HashMap<>()).put(defender, multiplier);
    }

    public static double getMultiplier(Type attackType, List<Type> defenderType) {
        double multiplier = 1.0;
        for (Type defType : defenderType) {
            multiplier *= table
                    .getOrDefault(attackType, Map.of())
                    .getOrDefault(defType, 1.0);
        }
        return multiplier;
    }
}
