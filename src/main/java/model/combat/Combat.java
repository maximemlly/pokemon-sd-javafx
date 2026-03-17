package model.combat;

import model.attack.Attack;
import model.player.Player;
import model.pokemon.*;
import model.pokemon.abilities.Blaze;
import model.pokemon.items.Balloon;
import model.pokemon.items.ChoiceBand;
import model.pokemon.items.ShellBell;
import model.pokemon.items.SunGlasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Combat {

    private final Player player;
    private final Player cpu;
    private final List<String> history;
    private final Random random;
    private int turnNumber;
    private boolean finished;
    private Player winner;

    public Combat(Player player, Player cpu) {
        this.player = player;
        this.cpu = cpu;
        this.history = new ArrayList<>();
        this.random = new Random();
        this.turnNumber = 1;
        this.finished = false;
        this.winner = null;
    }

    public void executeTurn(Attack playerAttack) {
        if (finished) return;

        log("=== Tour " + turnNumber + " ===");

        Pokemon playerPokemon = player.getActivePokemon();
        Pokemon cpuPokemon = cpu.getActivePokemon();
        Attack cpuAttack = cpu.chooseAttack();

        if (playerPokemon.getEffectiveSpeed() >= cpuPokemon.getEffectiveSpeed()) {
            if (random.nextBoolean() && playerPokemon.getEffectiveSpeed() == cpuPokemon.getEffectiveSpeed()) {
                executeAttack(cpuPokemon, playerPokemon, cpuAttack);
                if (!playerPokemon.isKO()) executeAttack(playerPokemon, cpuPokemon, playerAttack);
            } else {
                executeAttack(playerPokemon, cpuPokemon, playerAttack);
                if (!cpuPokemon.isKO()) executeAttack(cpuPokemon, playerPokemon, cpuAttack);
            }
        } else {
            executeAttack(cpuPokemon, playerPokemon, cpuAttack);
            if (!playerPokemon.isKO()) executeAttack(playerPokemon, cpuPokemon, playerAttack);
        }

        applyEndOfTurn(playerPokemon, cpuPokemon);

        handleKO(playerPokemon, player, cpu);
        handleKO(cpuPokemon, cpu, player);

        checkWinner();

        turnNumber++;
    }

    private void executeAttack(Pokemon attacker, Pokemon defender, Attack attack) {
        if (attacker.isKO()) return;

        if (attacker.getStatus() == Status.PARALYSIS && random.nextDouble() < 0.25) {
            log(attacker.getName() + " est paralysé et ne peut pas attaquer !");
            return;
        }

        if (!attack.usePp()) {
            log(attacker.getName() + " n'a plus de PP pour " + attack.getName() + " !");
            return;
        }

        if (isImmune(defender, attack)) {
            log(attack.getName() + " n'affecte pas " + defender.getName() + " !");
            return;
        }

        int damage = calculateDamage(attacker, defender, attack);
        double typeMultiplier = Type.getMultiplier(attack.getType(), defender.getTypes());

        defender.takeDamage(damage);

        log(attacker.getName() + " utilise " + attack.getName() + " !");
        log("  → " + damage + " dégâts infligés à " + defender.getName()
                + getEfficiencyLabel(typeMultiplier));

        attack.applyEffect(attacker, defender);
        if (attack.hasEffect()) {
            log("  → Effet : " + attack.getEffect().getDescription());
        }

        if (attacker.getItem() instanceof ShellBell shellBell) {
            shellBell.onDamageDealt(attacker, damage);
        }

        if (defender.getItem() != null) {
            defender.getItem().onAttackReceived(defender, attack.getType());
        }

        if (defender.getAbility() != null) {
            defender.getAbility().onAttackReceived(defender, attacker);
        }

        log("  → HP " + defender.getName() + " : " + defender.getCurrentHp() + "/" + defender.getBaseHp());
    }

    private int calculateDamage(Pokemon attacker, Pokemon defender, Attack attack) {
        double power = attack.getPower();
        double atkStat, defStat;

        if (attack.isPhysical()) {
            atkStat = attacker.getEffectiveAttack();
            defStat = defender.getEffectiveDefense();
        } else {
            atkStat = attacker.getEffectiveSpecialAttack();
            defStat = defender.getEffectiveSpecialDefense();
        }

        double typeMultiplier = Type.getMultiplier(attack.getType(), defender.getTypes());
        double randomFactor = 0.85 + random.nextDouble() * 0.15;

        double abilityBoost = getAbilityBoost(attacker, attack);

        double itemBoost = getItemBoost(attacker, attack);

        double damage = (power * (atkStat / defStat))
                * typeMultiplier
                * randomFactor
                * abilityBoost
                * itemBoost;

        return Math.max(1, (int) damage);
    }

    private double getAbilityBoost(Pokemon attacker, Attack attack) {
        if (attacker.getAbility() instanceof Blaze blaze) {
            if (blaze.isActive(attacker) && attack.getType() == Type.FIRE) {
                return Blaze.BOOST;
            }
        }
        return 1.0;
    }

    private double getItemBoost(Pokemon attacker, Attack attack) {
        if (attacker.getItem() instanceof SunGlasses sunGlasses) {
            return sunGlasses.getBoostFor(attack.getType());
        }
        if (attacker.getItem() instanceof ChoiceBand && attack.isPhysical()) {
            return ChoiceBand.BOOST;
        }
        return 1.0;
    }

    private boolean isImmune(Pokemon defender, Attack attack) {
        if (defender.getAbility() != null && defender.getAbility().isImmuneTo(attack.getType())) {
            return true;
        }
        if (defender.getItem() instanceof Balloon balloon) {
            return balloon.grantsImmunity(attack.getType());
        }
        return Type.getMultiplier(attack.getType(), defender.getTypes()) == 0.0;
    }

    private void applyEndOfTurn(Pokemon playerPokemon, Pokemon cpuPokemon) {
        applyEndOfTurnForOne(playerPokemon);
        applyEndOfTurnForOne(cpuPokemon);
    }

    private void applyEndOfTurnForOne(Pokemon pokemon) {
        if (pokemon.isKO()) return;

        int hpBefore = pokemon.getCurrentHp();

        pokemon.applyEndOfTurnStatus();
        if (pokemon.getItem() != null) pokemon.getItem().onTurnEnd(pokemon);
        if (pokemon.getAbility() != null) pokemon.getAbility().onTurnEnd(pokemon);

        int hpAfter = pokemon.getCurrentHp();
        if (hpAfter != hpBefore) {
            log(pokemon.getName() + " : " + hpAfter + "/" + pokemon.getBaseHp()
                    + " HP (fin de tour)");
        }
    }

    private void handleKO(Pokemon pokemon, Player owner, Player opponent) {
        if (!pokemon.isKO()) return;

        log(pokemon.getName() + " est KO !");
        pokemon.resetStages();

        if (owner.hasRemainingPokemon()) {
            owner.switchToNextAvailable();
            log(owner.getActivePokemon().getName() + " entre en combat !");
        }
    }

    public void switchPlayerPokemon(int index) {
        if (finished) return;
        Pokemon next = player.getTeam().getPokemon(index);
        if (next == null || next.isKO()) return;

        log("=== Tour " + turnNumber + " ===");
        log(player.getActivePokemon().getName() + " rentre !");
        player.switchPokemon(index);
        log(player.getActivePokemon().getName() + " entre en combat !");

        Attack cpuAttack = cpu.chooseAttack();
        executeAttack(cpu.getActivePokemon(), player.getActivePokemon(), cpuAttack);
        applyEndOfTurn(player.getActivePokemon(), cpu.getActivePokemon());
        handleKO(player.getActivePokemon(), player, cpu);
        handleKO(cpu.getActivePokemon(), cpu, player);
        checkWinner();
        turnNumber++;
    }

    private void checkWinner() {
        if (!cpu.hasRemainingPokemon()) {
            finished = true;
            winner = player;
            log("🏆 " + player.getName() + " remporte le combat !");
        } else if (!player.hasRemainingPokemon()) {
            finished = true;
            winner = cpu;
            log("💀 " + cpu.getName() + " remporte le combat !");
        }
    }

    private void log(String message) {
        history.add(message);
    }


    private String getEfficiencyLabel(double multiplier) {
        if (multiplier == 0.0) return " (sans effet)";
        if (multiplier >= 4.0) return " (extrêmement efficace !)";
        if (multiplier >= 2.0) return " (super efficace !)";
        if (multiplier <= 0.25) return " (pas du tout efficace...)";
        if (multiplier <= 0.5) return " (peu efficace...)";
        return "";
    }

    public List<String> getHistory() {
        return history;
    }

    public boolean isFinished() {
        return finished;
    }

    public Player getWinner() {
        return winner;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getCpu() {
        return cpu;
    }
}
