package service;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection instance;

    private DatabaseConnection() {}

    public static Connection getInstance() throws SQLException {
        if (instance == null || instance.isClosed()) {
            URL dbUrl = DatabaseConnection.class.getResource("/pokemon.db");
            if (dbUrl == null) throw new SQLException("Fichier pokemon.db introuvable dans resources/");
            instance = DriverManager.getConnection("jdbc:sqlite:" + dbUrl.getPath());
        }
        return instance;
    }

    public static void close() throws SQLException {
        if (instance != null && !instance.isClosed()) {
            instance.close();
        }
    }
}
