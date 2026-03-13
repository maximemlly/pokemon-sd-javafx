package model.attack;

public class Attack implements Effect {
    private String name;
    private int power;
    private String type;
    private String[] special = {"SPECIAL", "PHYSICAL", "STATUS"};

    private Attack(String name, int power, String type, String[] special) {
        this.name = name;
        this.power = power;
        this.type = type;
        this.special = special;
    }

}
