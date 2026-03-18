package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.pokemon.Pokemon;
import service.DataLoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamBuilderController {

    @FXML private ComboBox<Pokemon> pokemonSelector;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button startButton;
    @FXML private VBox teamContainer;
    @FXML private Label teamCountLabel;
    @FXML private Label statusLabel;

    private List<Pokemon> team = new ArrayList<>();
    private List<Pokemon> allPokemon;
    private DataLoader loader;

    @FXML
    public void initialize() {
        try {
            loader = new DataLoader();
            allPokemon = loader.loadAllPokemon();
            pokemonSelector.setItems(FXCollections.observableArrayList(allPokemon));
            updateTeamDisplay();
        } catch (SQLException e) {
            statusLabel.setText("Erreur de chargement des Pokémon");
            e.printStackTrace();
        }
    }

    @FXML
    private void addPokemon() {
        Pokemon selected = pokemonSelector.getValue();
        if (selected == null) {
            statusLabel.setText("Veuillez choisir un Pokémon");
            return;
        }
        if (team.size() >= 6) {
            statusLabel.setText("Équipe pleine (max 6)");
            return;
        }
        if (team.contains(selected)) {
            statusLabel.setText("Ce Pokémon est déjà dans l'équipe");
            return;
        }
        
        team.add(selected);
        updateTeamDisplay();
        statusLabel.setText("");
    }

    @FXML
    private void removePokemon() {
        if (team.isEmpty()) {
            statusLabel.setText("Aucun Pokémon à retirer");
            return;
        }
        team.remove(team.size() - 1);
        updateTeamDisplay();
        statusLabel.setText("");
    }

    private void updateTeamDisplay() {
        teamContainer.getChildren().clear();
        for (Pokemon pokemon : team) {
            Label label = new Label(pokemon.getName());
            teamContainer.getChildren().add(label);
        }
        teamCountLabel.setText("Équipe: " + team.size() + "/6");
    }

    @FXML
    private void startBattle() {
        if (team.size() < 3) {
            statusLabel.setText("Minimum 3 Pokémon requis");
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Battle.fxml"));
            Parent root = loader.load();
            
            // Passer l'équipe au BattleController
            BattleController battleController = loader.getController();
            battleController.setPlayerTeam(team);
            
            Stage stage = (Stage) ((Node) addButton).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            statusLabel.setText("Erreur de lancement du combat");
            e.printStackTrace();
        }
    }
}