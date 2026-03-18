package controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class HomeController {

    @FXML
    public void initialize() {
    }

    @FXML
    private void startGame() {
        // Load the next FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeamBuilder.fxml"));
        Parent root = loader.load();
        
        // Get the current stage from the event
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        
        // Create new scene and set it
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
