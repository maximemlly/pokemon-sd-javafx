module com.example.pokemonjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pokemonjavafx to javafx.fxml;
    exports com.example.pokemonjavafx;
}