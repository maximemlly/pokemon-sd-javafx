package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

public class HomeController {

    @FXML
    public void initialize() {
    }

    @FXML
    private void startGame() throws IOException {
        // Load the next FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeamBuilder.fxml"));
        Parent root = loader.load();
        
        // Get the current stage from the event
        EventObject event = null;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        // Create new scene and set it
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
