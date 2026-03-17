import model.pokemon.Pokemon;
import service.DataLoader;

import java.sql.SQLException;

void main() {
    try {
        DataLoader loader = new DataLoader();
        List<Pokemon> allPokemon = loader.loadAllPokemon();
        IO.println("Pokémon chargés : " + allPokemon.size());
    } catch (SQLException e) {
        e.printStackTrace();
    }

}
