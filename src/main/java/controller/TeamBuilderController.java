package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.attack.Attack;
import model.pokemon.Pokemon;
import service.DataLoader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamBuilderController {

    @FXML private ComboBox<Pokemon> pokemonSelector;
    @FXML private ComboBox<Attack> moveSlot1, moveSlot2, moveSlot3, moveSlot4;
    @FXML private Button addButton, removeButton, startButton, applyMovesButton;
    @FXML private VBox teamContainer;
    @FXML private Label teamCountLabel, statusLabel;

    private List<Pokemon> team = new ArrayList<>();
    private List<Pokemon> allPokemon;
    private List<Attack> allMoves;
    private DataLoader loader;
    private Pokemon selectedPokemonForMoves;

    @FXML
    public void initialize() {
        try {
            loader = new DataLoader();
            allPokemon = loader.loadAllPokemon();
            allMoves = loader.loadAllMoves(); // Ensure this method exists in DataLoader

            // Set items
            pokemonSelector.setItems(FXCollections.observableArrayList(allPokemon));
            
            // Initialize move slots
            moveSlot1.setItems(FXCollections.observableArrayList(allMoves));
            moveSlot2.setItems(FXCollections.observableArrayList(allMoves));
            moveSlot3.setItems(FXCollections.observableArrayList(allMoves));
            moveSlot4.setItems(FXCollections.observableArrayList(allMoves));

            updateTeamDisplay();
            
            // CRITICAL: This listener must be set here
            pokemonSelector.setOnAction(event -> onPokemonSelected());
            
            // Initial state check
            updateButtonStates(); 

        } catch (SQLException e) {
            statusLabel.setText("Erreur de chargement des Pokémon");
            e.printStackTrace();
        }
    }

    private void onPokemonSelected() {
        selectedPokemonForMoves = pokemonSelector.getValue();
        
        if (selectedPokemonForMoves != null) {
            // Pre-select default moves if available
            List<Attack> defaultMoves = selectedPokemonForMoves.getMoves();
            if (defaultMoves.size() > 0) moveSlot1.setValue(defaultMoves.get(0));
            if (defaultMoves.size() > 1) moveSlot2.setValue(defaultMoves.get(1));
            if (defaultMoves.size() > 2) moveSlot3.setValue(defaultMoves.get(2));
            if (defaultMoves.size() > 3) moveSlot4.setValue(defaultMoves.get(3));
            
            statusLabel.setText("Sélectionnez les attaques pour " + selectedPokemonForMoves.getName());
        } else {
            statusLabel.setText("Veuillez choisir un Pokémon");
        }
        
        // IMPORTANT: Trigger button update immediately after selection
        updateButtonStates();
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
        
        // Create a copy of the Pokémon with selected moves
        Pokemon pokemonToAdd = createPokemonWithMoves(selected);
        team.add(pokemonToAdd);
        
        updateTeamDisplay();
        updateButtonStates();
        statusLabel.setText("");
    }

    private Pokemon createPokemonWithMoves(Pokemon original) {
        List<Attack> selectedMoves = new ArrayList<>();
        if (moveSlot1.getValue() != null) selectedMoves.add(moveSlot1.getValue());
        if (moveSlot2.getValue() != null) selectedMoves.add(moveSlot2.getValue());
        if (moveSlot3.getValue() != null) selectedMoves.add(moveSlot3.getValue());
        if (moveSlot4.getValue() != null) selectedMoves.add(moveSlot4.getValue());
        
        return new Pokemon(original, selectedMoves);
    }

    @FXML
    private void applyMoves() {
        if (selectedPokemonForMoves == null) {
            statusLabel.setText("Veuillez d'abord choisir un Pokémon");
            return;
        }
        
        List<Attack> selectedMoves = new ArrayList<>();
        if (moveSlot1.getValue() != null) selectedMoves.add(moveSlot1.getValue());
        if (moveSlot2.getValue() != null) selectedMoves.add(moveSlot2.getValue());
        if (moveSlot3.getValue() != null) selectedMoves.add(moveSlot3.getValue());
        if (moveSlot4.getValue() != null) selectedMoves.add(moveSlot4.getValue());
        
        if (selectedMoves.isEmpty()) {
            statusLabel.setText("Veuillez choisir au moins une attaque");
            return;
        }
        
        statusLabel.setText("Aattaques appliquées à " + selectedPokemonForMoves.getName());
    }

    @FXML
    private void removePokemon() {
        if (team.isEmpty()) {
            statusLabel.setText("Aucun Pokémon à retirer");
            return;
        }
        team.remove(team.size() - 1);
        updateTeamDisplay();
        updateButtonStates();
        statusLabel.setText("");
    }

    private void updateTeamDisplay() {
        teamContainer.getChildren().clear();
        for (int i = 0; i < team.size(); i++) {
            Pokemon pokemon = team.get(i);
            HBox row = new HBox(10);
            
            Label nameLabel = new Label((i + 1) + ". " + pokemon.getName());
            Label movesLabel = new Label(pokemon.getMoves().size() + " attaques");
            
            row.getChildren().addAll(nameLabel, movesLabel);
            teamContainer.getChildren().add(row);
        }
        teamCountLabel.setText("Équipe: " + team.size() + "/6");
    }

    private void updateButtonStates() {
        boolean hasSelection = pokemonSelector.getValue() != null;
        boolean hasRoom = team.size() < 6;
        
        // Debug output (check your console/logs)
        System.out.println("--- Button State Check ---");
        System.out.println("Has Selection: " + hasSelection);
        System.out.println("Team Size: " + team.size());
        System.out.println("Has Room: " + hasRoom);
        
        // Logic: Enable if we have a selection AND room in the team
        boolean canAdd = hasSelection && hasRoom;
        
        addButton.setDisable(!canAdd);
        
        // Also update other buttons
        startButton.setDisable(team.size() < 3);
        removeButton.setDisable(team.isEmpty());
        
        System.out.println("Add Button Enabled: " + !addButton.isDisabled());
        System.out.println("------------------------");
    }

    @FXML
    private void startBattle() {
        if (team.size() < 3) {
            statusLabel.setText("Minimum 3 Pokémon requis");
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Battle.fxml"));
            Parent root = loader.load();
            
            // Pass the team to the BattleController
            BattleController battleController = loader.getController();
            battleController.setPlayerTeam(team);
            
            // You'll need to create the Combat object here
            // For now, we'll assume it's done in BattleController
            
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