package model.player;

import model.attack.Attack;
import model.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CPU extends Player {

    private final Random random = new Random();

    public CPU(String name, Team team) {
        super(name, team);
    }

    @Override
    public Attack chooseAttack() {
        Pokemon active = getActivePokemon();
        if (active == null) return null;

        List<Attack> usable = new ArrayList<>();
        for (Attack a : active.getMoves()) {
            if (a.getCurrentPp() > 0) usable.add(a);
        }

        if (usable.isEmpty()) return null;

        return usable.get(random.nextInt(usable.size()));
    }
}