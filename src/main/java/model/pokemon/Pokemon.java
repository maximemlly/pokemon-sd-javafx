package model.pokemon;

import model.attack.Attack;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {

    private final String name;
    private final int baseHp;
    private int currentHp;

    private final int attack;
    private final int defense;
    private final int specialAttack;
    private final int specialDefense;
    private final int speed;

    private final List<Type> types;
    private final List<Attack> moves;

    private Status status;
    private Ability ability;
    private Item item;

    private int attackStage;
    private int defenseStage;
    private int specialAttackStage;
    private int specialDefenseStage;
    private int speedStage;

    private int lastDamageTaken;

    public Pokemon(String name,
                   int hp, int attack, int defense,
                   int specialAttack, int specialDefense, int speed,
                   List<Type> types, List<Attack> moves,
                   Ability ability, Item item) {
        this.name = name;
        this.baseHp = hp;
        this.currentHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.types = new ArrayList<>(types);
        this.moves = new ArrayList<>(moves);
        this.item = item;
        this.attackStage = 0;
        this.defenseStage = 0;
        this.specialAttackStage = 0;
        this.specialDefenseStage = 0;
        this.speedStage = 0;
    }

    public Pokemon(Pokemon original, List<Attack> newMoves) {
        this.name = original.name;
        this.baseHp = original.baseHp;
        this.currentHp = original.baseHp;
        this.attack = original.attack;
        this.defense = original.defense;
        this.specialAttack = original.specialAttack;
        this.specialDefense = original.specialDefense;
        this.speed = original.speed;
        this.types = new ArrayList<>(original.types);
        this.moves = new ArrayList<>(newMoves);
        this.ability = original.ability;
        this.item = original.item;
        this.status = Status.NONE;
        // Reset stages
        this.attackStage = 0;
        this.defenseStage = 0;
        this.specialAttackStage = 0;
        this.specialDefenseStage = 0;
        this.speedStage = 0;
        this.lastDamageTaken = 0;
    }

    public int getEffectiveAttack() {
        double multiplier = getStageMultiplier(attackStage);
        if(status == Status.BURN) {
            multiplier *= 0.5;
        }
        return (int)(defense * getStageMultiplier(specialAttackStage));
    }

    public int getEffectiveDefense() {
        return (int)(specialDefense * getStageMultiplier(specialDefenseStage));
    }

    public int getEffectiveSpecialAttack() {
        return (int) (specialAttack * getStageMultiplier(specialAttackStage));
    }

    public int getEffectiveSpecialDefense() {
        return (int) (specialDefense * getStageMultiplier(specialDefenseStage));
    }

    public int getEffectiveSpeed() {
        double multiplier = getStageMultiplier(speedStage);
        if(status == Status.PARALYSIS) {
            multiplier *= 0.5;
        }
        return (int)(speed * multiplier);
    }

    private double getStageMultiplier(int stage) {
        double[] multipliers = { 0.25, 0.28, 0.33, 0.40, 0.50, 0.66, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
        return multipliers[stage + 6];
    }

    public void takeDamage(int damage) {
        int actual = Math.min(currentHp, damage);
        currentHp = Math.max(0, currentHp - damage);
        this.lastDamageTaken = actual;
    }

    public void heal(int amount) {
        currentHp = Math.min(baseHp, currentHp + amount);
    }

    public boolean isKO() {
        return currentHp <= 0;
    }

    public void applyEndOfTurnStatus() {
        switch(status) {
            case POISON -> takeDamage(baseHp / 8);
            case BURN -> takeDamage(baseHp / 16);
            default -> {}
        }
    }

    public void modifyStage(Stat stat, int delta) {
        switch(stat) {
            case ATTACK -> attackStage = clampStage(attackStage + delta);
            case DEFENSE -> defenseStage = clampStage(defenseStage + delta);
            case SPECIAL_ATTACK -> specialAttackStage = clampStage(specialAttackStage + delta);
            case SPECIAL_DEFENSE -> specialDefenseStage = clampStage(specialDefenseStage + delta);
            case SPEED -> speedStage = clampStage(speedStage + delta);
        }
    }

    private int clampStage(int stage) {
        return Math.max(-6, Math.min(6, stage));
    }

    public void resetStages() {
        attackStage = 0;
        defenseStage = 0;
        specialAttackStage = 0;
        specialDefenseStage = 0;
        speedStage = 0;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public List<Type> getTypes() {
        return types;
    }

    public List<Attack> getMoves() {
        return moves;
    }

    public Status getStatus() {
        return status;
    }

    public Ability getAbility() {
        return ability;
    }

    public Item getItem() {
        return item;
    }

    public int getAttackStage() {
        return attackStage;
    }

    public int getDefenseStage() {
        return defenseStage;
    }

    public int getSpecialAttackStage() {
        return specialAttackStage;
    }

    public int getLastDamageTaken() {
        return(lastDamageTaken);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    @Override
    public String toString() {
        return name + " [" + currentHp + "/" + baseHp + " HP] " + status;
    }
}
