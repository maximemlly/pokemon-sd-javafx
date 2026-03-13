package model.pokemon;

import java.util.List;

public class Pokemon {
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private int hp;
    private List<Type> types;

    private Pokemon(
            int attack,
            int defense,
            int specialAttack,
            int specialDefense,
            int speed,
            int hp,
            List<Type> types) {

        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.hp = hp;
        this.types = types;
    }
}
