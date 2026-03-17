package service;

import model.attack.Attack;
import model.attack.Effect;
import model.attack.effect.BurnEffect;
import model.attack.effect.DrainEffect;
import model.attack.effect.PoisonEffect;
import model.pokemon.Ability;
import model.pokemon.Pokemon;
import model.pokemon.Type;

import java.sql.Connection;
import java.sql.SQLException;

import java.sql.*;
import java.util.*;

public class DataLoader {
    private final Connection connection;

    public DataLoader() throws SQLException {
        this.connection = DatabaseConnection.getInstance();
    }

    public List<Pokemon> loadAllPokemon() throws SQLException {
        Map<Integer, Ability> abilities = loadAbilities();
        Map<Integer, Attack> moves = loadMoves();
        return loadPokemon(abilities, moves);
    }

    private Map<Integer, Ability> loadAbilities() throws SQLException {
        Map<Integer, Ability> abilities = new HashMap<>();
        String query = "SELECT id, name, description FROM abilities";

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()) {
                int  id = rs.getInt("id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                abilities.put(id, new Ability(name, desc));
            }
        }
        return abilities;
    }

    private Map<Integer, Attack> loadMoves() throws SQLException {
        Map<Integer, Attack> moves = new HashMap<>();
        String query = "SELECT id, name, power, type, is_physical, pp, effect_type, effect_chance FROM moves";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int power = rs.getInt("power");
                Type type = Type.valueOf(rs.getString("type"));
                boolean isPhysical = rs.getInt("is_physical") == 1;
                int pp = rs.getInt("pp");
                Effect effect = parseEffect(rs.getString("effect_type"), rs.getDouble("effect_chance"));
                moves.put(id, new Attack(name, power, type, isPhysical, pp, effect));
            }
        }
        return moves;
    }

    private Effect parseEffect(String effectType, double value) {
        if(effectType == null) return null;
        return switch (effectType) {
            case "BURN" -> new BurnEffect(value);
            case "POISON" -> new PoisonEffect(value);
            case "RECOIL" -> new PoisonEffect(value);
            case "DRAIN" -> new DrainEffect(value);
            default -> null;
        };
    }

    private List<Pokemon> loadPokemon(Map<Integer, Ability> abilities,
                                      Map<Integer, Attack> moves) throws SQLException {
        List<Pokemon> pokemons = new ArrayList<>();
        String query = """
                SELECT id, name, hp, attack, defense,
                       special_attack, special_defense, speed, ability_id
                FROM pokemon
                """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int hp = rs.getInt("hp");
                int atk = rs.getInt("attack");
                int def = rs.getInt("defense");
                int spAtk = rs.getInt("special_attack");
                int spDef = rs.getInt("special_defense");
                int speed = rs.getInt("speed");
                Ability ability = abilities.get(rs.getInt("ability_id"));

                List<Type> types = loadPokemonTypes(id);
                List<Attack> pokemonMoves = loadPokemonMoves(id, moves);

                pokemons.add(new Pokemon(name, hp, atk, def, spAtk, spDef, speed,
                        types, pokemonMoves, ability, null));
            }
        }
        return pokemons;
    }

    private List<Type> loadPokemonTypes(int pokemonId) throws SQLException {
        List<Type> types = new ArrayList<>();
        String sql = "SELECT type FROM pokemon_types WHERE pokemon_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pokemonId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                types.add(Type.valueOf(rs.getString("type")));
            }
        }
        return types;
    }

    private List<Attack> loadPokemonMoves(int pokemonId, Map<Integer, Attack> allMoves) throws SQLException {
        List<Attack> moves = new ArrayList<>();
        String sql = "SELECT move_id FROM pokemon_moves WHERE pokemon_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pokemonId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attack move = allMoves.get(rs.getInt("move_id"));
                if (move != null) moves.add(move);
            }
        }
        return moves;
    }
}
