package controller;

import java.util.List;

import model.pokemon.Pokemon;

public class BattleController {
        
    private List<Pokemon> playerTeam;

    
    public void setPlayerTeam(List<Pokemon> playerTeam) {
        this.playerTeam = playerTeam;
        // update UI / state as needed, e.g. refresh the battle view
    }
    
}
