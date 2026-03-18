package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent; // Ajoute cette import

import java.io.IOException;

public class HomeController {

    @FXML
    public void initialize() {
        // Initialisation si nécessaire
    }

    @FXML
    private void startGame(ActionEvent event) throws IOException { // Change ici
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeamBuilder.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
