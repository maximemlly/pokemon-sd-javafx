/* import model.pokemon.Pokemon;
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
*/



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Charger le fichier Home.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        AnchorPane root = loader.load();
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Pokémon Showdown! JavaFX Edition");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}