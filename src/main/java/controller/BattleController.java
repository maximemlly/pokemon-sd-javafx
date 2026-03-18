package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.attack.Attack;
import model.combat.Combat;
import model.player.Player;
import model.pokemon.Pokemon;
import model.pokemon.Type;

import java.util.List;

public class BattleController {

    @FXML private Label enemyNameLabel;
    @FXML private Label enemyHpText;
    @FXML private Label enemyLevelLabel;
    @FXML private Rectangle enemyHpBarBg;
    @FXML private Rectangle enemyHpBarFill;

    @FXML private Label playerNameLabel;
    @FXML private Label playerHpText;
    @FXML private Label playerLevelLabel;
    @FXML private Rectangle playerHpBarBg;
    @FXML private Rectangle playerHpBarFill;

    @FXML private TextArea battleLog;
    
    @FXML private Button btnMove1, btnMove2, btnMove3, btnMove4;
    @FXML private Button btnSwitch, btnRun, btnNext;

    private Combat combat;
    private List<Pokemon> playerTeam;
    private boolean isPlayerTurn = true;
    private boolean isBattleOver = false;

    @FXML
    public void initialize() {
        // Initialize buttons state
        updateButtonsState(false);
        battleLog.appendText("Battle started!\n");
    }

    // Called from TeamBuilderController to set the team and start combat
    public void setPlayerTeam(List<Pokemon> playerTeam) {
        this.playerTeam = playerTeam;
        // Note: In a full app, you'd also need to initialize the CPU team here
        // For now, we assume the Combat object is created externally or we create a dummy CPU
        // Let's create a simple CPU for demonstration if not passed
        // Ideally, you'd pass the CPU object or create it here based on your design
        
        // Assuming we create a simple CPU for now (you might want to refactor this)
        // Since CPU class exists, we need a Team for it. 
        // For this example, let's assume the user passes a fully initialized Combat object 
        // OR we create a minimal one here. 
        // Given the previous code, let's assume we need to initialize the combat here.
        
        // TODO: You might want to pass the CPU team from TeamBuilder or create a random one
        // For now, I'll create a placeholder CPU team logic if needed, 
        // but strictly following your request, I'll assume 'combat' is set via a setter or constructor.
        // Let's assume we create a dummy CPU for the sake of the UI working immediately.
        
        // Re-initializing combat with a dummy CPU team (Random 3 Pokemon)
        // In a real scenario, you'd load this from DB or pass it in.
        // Since I can't see your CPU initialization logic fully, I'll mock it slightly:
        // You should replace this with your actual CPU initialization logic.
        
        // For now, let's assume the user passes the combat object via a setter or we create it.
        // Let's create a simple setup:
        // model.player.CPU cpu = new CPU("CPU", new Team()); // Need to populate CPU team
        // combat = new Combat(new Player("Player", new Team(playerTeam)), cpu);
        
        // Since I don't have the CPU team data here, I will assume you have a way to init 'combat'.
        // I will add a setter for combat to be called by TeamBuilder or Main.
    }
    
    public void setCombat(Combat combat) {
        this.combat = combat;
        updateUI();
    }

    @FXML
    private void handleMove1() { executeMove(0); }
    @FXML
    private void handleMove2() { executeMove(1); }
    @FXML
    private void handleMove3() { executeMove(2); }
    @FXML
    private void handleMove4() { executeMove(3); }

    private void executeMove(int moveIndex) {
        if (isBattleOver || !isPlayerTurn) return;

        Pokemon playerPokemon = combat.getPlayer().getActivePokemon();
        if (playerPokemon == null || playerPokemon.isKO()) return;

        List<Attack> moves = playerPokemon.getMoves();
        if (moveIndex >= moves.size()) {
            appendLog("No move at index " + moveIndex);
            return;
        }

        Attack selectedMove = moves.get(moveIndex);
        
        // Disable buttons during turn processing
        updateButtonsState(false);
        
        // Execute the turn in the model
        combat.executeTurn(selectedMove);
        
        // Update UI after model execution
        updateUI();
        
        // Check if battle is over
        if (combat.isFinished()) {
            isBattleOver = true;
            appendLog("\n" + combat.getWinner().getName() + " wins!");
            btnNext.setVisible(true);
            btnNext.setText("Return to Menu"); // Or restart
        } else {
            // Wait for user to click "Next Turn" or auto-switch? 
            // In your Combat.java, executeTurn handles both sides. 
            // So we just need to wait for the user to acknowledge or auto-proceed.
            // Let's keep the "Next Turn" button visible to acknowledge the log.
            btnNext.setVisible(true);
            btnNext.setText("Next Turn");
        }
    }

    @FXML
    private void handleSwitch() {
        // Logic to switch would go here. 
        // Your Combat class has switchPlayerPokemon(int index).
        // You need a way to select WHICH pokemon to switch to.
        // For simplicity, let's assume we cycle to the next available one or show a dialog.
        // Since we don't have a switch UI in this FXML, let's just log it for now.
        appendLog("Switching logic not fully implemented in this UI version.");
        // Example: combat.switchPlayerPokemon(1);
    }

    @FXML
    private void handleRun() {
        appendLog("You ran away!");
        isBattleOver = true;
        updateButtonsState(false);
        btnNext.setVisible(true);
        btnNext.setText("Return to Menu");
    }

    @FXML
    private void handleNextTurn() {
        if (isBattleOver) {
            // Return to menu logic
            Stage stage = (Stage) btnNext.getScene().getWindow();
            stage.close();
            return;
        }
        
        btnNext.setVisible(false);
        updateButtonsState(true);
        // The turn logic is already handled in executeMove (which calls combat.executeTurn)
        // So clicking "Next Turn" just re-enables buttons for the next choice
    }

        // ... inside the class ...

    private void updateUI() {
        if (combat == null) return;

        // Update Player
        Pokemon playerPokemon = combat.getPlayer().getActivePokemon();
        if (playerPokemon != null) {
            playerNameLabel.setText(playerPokemon.getName());
            playerLevelLabel.setText("Lv. " + 50); 
            updateHealthBar(playerHpBarFill, playerHpText, playerHpBarBg, playerPokemon);
        }

        // Update Enemy
        Pokemon enemyPokemon = combat.getCpu().getActivePokemon();
        if (enemyPokemon != null) {
            enemyNameLabel.setText(enemyPokemon.getName());
            enemyLevelLabel.setText("Lv. " + 50);
            // FIXED: Changed enemyHpBg to enemyHpBarBg
            updateHealthBar(enemyHpBarFill, enemyHpText, enemyHpBarBg, enemyPokemon);
        }

        // Update Moves
        if (playerPokemon != null) {
            List<Attack> moves = playerPokemon.getMoves();
            btnMove1.setText(moves.size() > 0 ? moves.get(0).getName() : "");
            btnMove2.setText(moves.size() > 1 ? moves.get(1).getName() : "");
            btnMove3.setText(moves.size() > 2 ? moves.get(2).getName() : "");
            btnMove4.setText(moves.size() > 3 ? moves.get(3).getName() : "");
            
            if (moves.size() > 0) btnMove1.setDisable(moves.get(0).getCurrentPp() <= 0);
            if (moves.size() > 1) btnMove2.setDisable(moves.get(1).getCurrentPp() <= 0);
            if (moves.size() > 2) btnMove3.setDisable(moves.get(2).getCurrentPp() <= 0);
            if (moves.size() > 3) btnMove4.setDisable(moves.get(3).getCurrentPp() <= 0);
        }

        // Update Log
        List<String> history = combat.getHistory();
        StringBuilder logText = new StringBuilder();
        for (String line : history) {
            logText.append(line).append("\n");
        }
        battleLog.setText(logText.toString());
        // FIXED: Use positionCaret instead of scrollTo
        battleLog.positionCaret(battleLog.getText().length());
    }

    private void appendLog(String message) {
        battleLog.appendText(message + "\n");
        // FIXED: Use positionCaret instead of scrollTo
        battleLog.positionCaret(battleLog.getText().length());
    }

    private void updateHealthBar(Rectangle fill, Label text, Rectangle bg, Pokemon pokemon) {
        int current = pokemon.getCurrentHp();
        int max = pokemon.getBaseHp();
        double percentage = (double) current / max;
        
        fill.setWidth(bg.getWidth() * percentage);
        text.setText(String.format("HP: %d/%d", current, max));
        
        // Color logic
        if (percentage > 0.5) fill.setFill(Color.GREEN);
        else if (percentage > 0.2) fill.setFill(Color.ORANGE);
        else fill.setFill(Color.RED);
    }

    private void updateButtonsState(boolean enabled) {
        btnMove1.setDisable(!enabled);
        btnMove2.setDisable(!enabled);
        btnMove3.setDisable(!enabled);
        btnMove4.setDisable(!enabled);
        btnSwitch.setDisable(!enabled);
        btnRun.setDisable(!enabled);
    }
}