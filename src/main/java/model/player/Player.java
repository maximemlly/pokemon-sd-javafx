package model.player;

import model.attack.Attack;
import model.pokemon.Pokemon;

public abstract class Player {

    protected final String name;
    protected final Team team;

    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public Pokemon getActivePokemon() {
        return team.getActivePokemon();
    }

    public boolean hasRemainingPokemon() {
        return team.hasRemainingPokemon();
    }

    public void switchPokemon(int index) {
        team.setActiveIndex(index);
    }

    // Utilisé par Combat pour le CPU, pour le joueur humain ton contrôleur passera l'attaque choisie
    public abstract Attack chooseAttack();

    // Pour Combat.switchPlayerPokemon
    public void switchToNextAvailable() {
        for (int i = 0; i < team.size(); i++) {
            Pokemon p = team.getPokemon(i);
            if (p != null && !p.isKO()) {
                team.setActiveIndex(i);
                return;
            }
        }
    }
}