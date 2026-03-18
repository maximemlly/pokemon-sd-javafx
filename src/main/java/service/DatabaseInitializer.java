package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String SQL_SCRIPT_PATH = "/data/populate_database.sql";

    /**
     * Initializes the database if it doesn't exist or is empty.
     * Reads the SQL script from resources and executes it.
     */
    public static void initialize(Connection connection) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is null.");
        }

        try {
            // Check if tables already exist to avoid re-initialization
            if (tablesExist(connection)) {
                System.out.println("Database already initialized. Skipping population.");
                return;
            }

            System.out.println("Initializing database...");
            String sqlScript = loadSqlScript();
            
            // Split script by semicolons and execute statements
            // Note: This is a simple splitter. For complex SQL with semicolons in strings, 
            // a proper parser is better, but this works for our generated script.
            String[] statements = sqlScript.split(";");
            
            try (Statement stmt = connection.createStatement()) {
                for (String statement : statements) {
                    String trimmed = statement.trim();
                    if (!trimmed.isEmpty()) {
                        // Ensure the statement ends with a semicolon for SQLite
                        if (!trimmed.endsWith(";")) {
                            trimmed += ";";
                        }
                        stmt.execute(trimmed);
                    }
                }
            }
            
            System.out.println("Database initialized successfully.");
            
        } catch (IOException e) {
            throw new SQLException("Failed to load SQL script: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new SQLException("Failed to initialize database: " + e.getMessage(), e);
        }
    }

    /**
     * Checks if the required tables exist in the database.
     */
    private static boolean tablesExist(Connection connection) throws SQLException {
        String[] requiredTables = {"pokemon", "moves", "abilities", "pokemon_types", "pokemon_moves"};
        
        try (ResultSet rs = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME").toLowerCase();
                for (String required : requiredTables) {
                    if (tableName.equals(required.toLowerCase())) {
                        return true; // Found at least one table, assuming others exist too
                    }
                }
            }
        }
        return false;
    }

    /**
     * Loads the SQL script from the resources folder.
     */
    private static String loadSqlScript() throws IOException {
        try (InputStream inputStream = DatabaseInitializer.class.getResourceAsStream(SQL_SCRIPT_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            
            if (inputStream == null) {
                throw new IOException("SQL script not found at: " + SQL_SCRIPT_PATH);
            }

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }
            return sql.toString();
        }
    }
}