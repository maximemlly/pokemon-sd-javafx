-- ============================================
-- POKEMON DATABASE POPULATION SCRIPT
-- ============================================

-- Drop tables if they exist (to recreate fresh)
DROP TABLE IF EXISTS pokemon_moves;
DROP TABLE IF EXISTS pokemon_types;
DROP TABLE IF EXISTS moves;
DROP TABLE IF EXISTS pokemon;
DROP TABLE IF EXISTS abilities;

-- ============================================
-- ABILITIES TABLE
-- ============================================
CREATE TABLE abilities (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT
);

INSERT INTO abilities (id, name, description) VALUES
(1, 'Blaze', 'Boosts Fire-type attacks when HP is below 1/3'),
(2, 'Immunity', 'Cannot be poisoned'),
(3, 'Levitate', 'Immune to Ground-type attacks'),
(4, 'Overgrow', 'Boosts Grass-type attacks when HP is below 1/3'),
(5, 'Torrent', 'Boosts Water-type attacks when HP is below 1/3'),
(6, 'Static', '30% chance to paralyze attackers on contact'),
(7, 'Intimidate', 'Lowers opponent''s Attack when entering battle'),
(8, 'Adaptability', 'STAB attacks have increased power');

-- ============================================
-- MOVES TABLE
-- ============================================
CREATE TABLE moves (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    power INTEGER,
    type TEXT NOT NULL,
    is_physical INTEGER DEFAULT 1,
    pp INTEGER,
    effect_type TEXT,
    effect_chance REAL
);

INSERT INTO moves (id, name, power, type, is_physical, pp, effect_type, effect_chance) VALUES
-- FIRE TYPE
(1, 'Flamethrower', 90, 'FIRE', 0, 15, 'BURN', 0.1),
(2, 'Fire Blast', 110, 'FIRE', 0, 5, 'BURN', 0.1),
(3, 'Flame Wheel', 60, 'FIRE', 1, 25, 'BURN', 0.1),
(4, 'Ember', 40, 'FIRE', 0, 25, 'BURN', 0.1),
(5, 'Fire Punch', 75, 'FIRE', 1, 15, 'BURN', 0.1),

-- WATER TYPE
(6, 'Hydro Pump', 110, 'WATER', 0, 5, NULL, NULL),
(7, 'Surf', 90, 'WATER', 0, 15, NULL, NULL),
(8, 'Water Gun', 40, 'WATER', 0, 25, NULL, NULL),
(9, 'Bubble Beam', 65, 'WATER', 0, 20, NULL, NULL),
(10, 'Waterfall', 80, 'WATER', 1, 15, NULL, NULL),

-- GRASS TYPE
(11, 'Solar Beam', 120, 'GRASS', 0, 10, NULL, NULL),
(12, 'Leaf Blade', 90, 'GRASS', 1, 15, NULL, NULL),
(13, 'Razor Leaf', 55, 'GRASS', 1, 25, NULL, NULL),
(14, 'Vine Whip', 45, 'GRASS', 1, 25, NULL, NULL),
(15, 'Giga Drain', 75, 'GRASS', 0, 10, 'DRAIN', 0.5),

-- ELECTRIC TYPE
(16, 'Thunderbolt', 90, 'ELECTRIC', 0, 15, 'PARALYSIS', 0.1),
(17, 'Thunder', 110, 'ELECTRIC', 0, 10, 'PARALYSIS', 0.3),
(18, 'Thunder Punch', 75, 'ELECTRIC', 1, 15, 'PARALYSIS', 0.1),
(19, 'Spark', 65, 'ELECTRIC', 1, 20, 'PARALYSIS', 0.3),
(20, 'Volt Tackle', 120, 'ELECTRIC', 1, 15, 'RECOIL', 0.33),

-- NORMAL TYPE
(21, 'Tackle', 40, 'NORMAL', 1, 35, NULL, NULL),
(22, 'Body Slam', 85, 'NORMAL', 1, 15, 'PARALYSIS', 0.3),
(23, 'Hyper Beam', 150, 'NORMAL', 0, 5, NULL, NULL),
(24, 'Quick Attack', 40, 'NORMAL', 1, 30, NULL, NULL),
(25, 'Slash', 70, 'NORMAL', 1, 20, NULL, NULL),

-- POISON TYPE
(26, 'Sludge Bomb', 90, 'POISON', 0, 10, 'POISON', 0.3),
(27, 'Poison Jab', 80, 'POISON', 1, 20, 'POISON', 0.3),
(28, 'Toxic', 0, 'POISON', 0, 10, 'POISON', 1.0),

-- GROUND TYPE
(29, 'Earthquake', 100, 'GROUND', 1, 10, NULL, NULL),
(30, 'Dig', 80, 'GROUND', 1, 10, NULL, NULL),
(31, 'Mud Shot', 55, 'GROUND', 1, 15, NULL, NULL),

-- FLYING TYPE
(32, 'Air Slash', 75, 'FLYING', 0, 15, 'FLINCH', 0.3),
(33, 'Brave Bird', 120, 'FLYING', 1, 15, 'RECOIL', 0.33),
(34, 'Wing Attack', 60, 'FLYING', 1, 35, NULL, NULL),

-- PSYCHIC TYPE
(35, 'Psychic', 90, 'PSYCHIC', 0, 10, NULL, NULL),
(36, 'Psybeam', 65, 'PSYCHIC', 0, 20, NULL, NULL),
(37, 'Confusion', 50, 'PSYCHIC', 0, 25, NULL, NULL),

-- DARK TYPE
(38, 'Dark Pulse', 80, 'DARK', 0, 15, 'FLINCH', 0.2),
(39, 'Crunch', 80, 'DARK', 1, 15, NULL, NULL),
(40, 'Night Slash', 70, 'DARK', 1, 15, NULL, NULL),

-- STEEL TYPE
(41, 'Iron Head', 80, 'STEEL', 1, 15, 'FLINCH', 0.3),
(42, 'Flash Cannon', 80, 'STEEL', 0, 10, NULL, NULL),
(43, 'Metal Claw', 50, 'STEEL', 1, 35, NULL, NULL),

-- DRAGON TYPE
(44, 'Dragon Claw', 80, 'DRAGON', 1, 15, NULL, NULL),
(45, 'Dragon Pulse', 85, 'DRAGON', 0, 10, NULL, NULL),
(46, 'Outrage', 120, 'DRAGON', 1, 10, NULL, NULL),

-- FIGHTING TYPE
(47, 'Close Combat', 120, 'FIGHTING', 1, 5, NULL, NULL),
(48, 'Brick Break', 75, 'FIGHTING', 1, 15, NULL, NULL),
(49, 'Focus Blast', 120, 'FIGHTING', 0, 5, NULL, NULL),

-- ROCK TYPE
(50, 'Rock Slide', 75, 'ROCK', 1, 10, 'FLINCH', 0.3),
(51, 'Stone Edge', 100, 'ROCK', 1, 5, NULL, NULL),
(52, 'Rock Throw', 50, 'ROCK', 1, 15, NULL, NULL);

-- ============================================
-- POKEMON TABLE
-- ============================================
CREATE TABLE pokemon (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    hp INTEGER,
    attack INTEGER,
    defense INTEGER,
    special_attack INTEGER,
    special_defense INTEGER,
    speed INTEGER,
    ability_id INTEGER,
    FOREIGN KEY (ability_id) REFERENCES abilities(id)
);

-- Charizard (Fire/Flying)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(1, 'Charizard', 78, 84, 78, 109, 85, 100, 1);

-- Blastoise (Water)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(2, 'Blastoise', 79, 83, 100, 85, 105, 78, 5);

-- Venusaur (Grass/Poison)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(3, 'Venusaur', 80, 82, 83, 100, 100, 80, 4);

-- Pikachu (Electric)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(4, 'Pikachu', 35, 55, 40, 50, 50, 90, 6);

-- Gengar (Ghost/Poison)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(5, 'Gengar', 60, 65, 60, 130, 75, 110, 3);

-- Golem (Rock/Ground)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(6, 'Golem', 80, 120, 130, 55, 65, 45, NULL);

-- Alakazam (Psychic)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(7, 'Alakazam', 55, 50, 45, 135, 95, 120, NULL);

-- Dragonite (Dragon/Flying)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(8, 'Dragonite', 91, 134, 95, 100, 100, 80, 7);

-- Tyranitar (Rock/Dark)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(9, 'Tyranitar', 100, 134, 110, 95, 100, 61, 7);

-- Snorlax (Normal)
INSERT INTO pokemon (id, name, hp, attack, defense, special_attack, special_defense, speed, ability_id) VALUES
(10, 'Snorlax', 160, 110, 65, 65, 110, 30, NULL);

-- ============================================
-- POKEMON TYPES TABLE
-- ============================================
CREATE TABLE pokemon_types (
    pokemon_id INTEGER,
    type TEXT,
    FOREIGN KEY (pokemon_id) REFERENCES pokemon(id)
);

INSERT INTO pokemon_types (pokemon_id, type) VALUES
(1, 'FIRE'), (1, 'FLYING'),
(2, 'WATER'),
(3, 'GRASS'), (3, 'POISON'),
(4, 'ELECTRIC'),
(5, 'GHOST'), (5, 'POISON'),
(6, 'GROUND'), (6, 'ROCK'),
(7, 'PSYCHIC'),
(8, 'DRAGON'), (8, 'FLYING'),
(9, 'ROCK'), (9, 'DARK'),
(10, 'NORMAL');

-- ============================================
-- POKEMON MOVES TABLE
-- ============================================
CREATE TABLE pokemon_moves (
    pokemon_id INTEGER,
    move_id INTEGER,
    FOREIGN KEY (pokemon_id) REFERENCES pokemon(id),
    FOREIGN KEY (move_id) REFERENCES moves(id)
);

-- Charizard moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(1, 1), (1, 32), (1, 29), (1, 21);

-- Blastoise moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(2, 6), (2, 7), (2, 29), (2, 21);

-- Venusaur moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(3, 11), (3, 12), (3, 26), (3, 21);

-- Pikachu moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(4, 16), (4, 18), (4, 22), (4, 24);

-- Gengar moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(5, 35), (5, 26), (5, 38), (5, 21);

-- Golem moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(6, 29), (6, 51), (6, 21), (6, 47);

-- Alakazam moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(7, 35), (7, 36), (7, 49), (7, 21);

-- Dragonite moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(8, 44), (8, 33), (8, 29), (8, 21);

-- Tyranitar moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(9, 39), (9, 51), (9, 29), (9, 21);

-- Snorlax moves
INSERT INTO pokemon_moves (pokemon_id, move_id) VALUES
(10, 23), (10, 22), (10, 47), (10, 21);

-- ============================================
-- CREATE INDEXES FOR BETTER PERFORMANCE
-- ============================================
CREATE INDEX idx_pokemon_types ON pokemon_types(pokemon_id);
CREATE INDEX idx_pokemon_moves ON pokemon_moves(pokemon_id);