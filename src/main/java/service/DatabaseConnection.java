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
            if (dbUrl == null) {
                // If the file doesn't exist in resources, we might need to create it in the working directory
                // Or ensure the path is correct. Usually, SQLite creates the file if it doesn't exist.
                // Let's try to connect to the path directly.
                // If running from IDE, resources might be in target/classes.
                // The JDBC URL "jdbc:sqlite:pokemon.db" usually looks in the current working directory.
                // Let's try to get the absolute path if it's a file.
                instance = DriverManager.getConnection("jdbc:sqlite:pokemon.db");
            } else {
                instance = DriverManager.getConnection("jdbc:sqlite:" + dbUrl.getPath());
            }
            
            // Initialize the database if it's a fresh connection
            DatabaseInitializer.initialize(instance);
        }
        return instance;
    }

    public static void close() throws SQLException {
        if (instance != null && !instance.isClosed()) {
            instance.close();
        }
    }
}