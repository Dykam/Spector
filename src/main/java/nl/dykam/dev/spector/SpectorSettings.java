package nl.dykam.dev.spector;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SpectorSettings {
    private GameMode gameMode = GameMode.SURVIVAL;
    private boolean fly = false;
    private float flySpeed = 0.2f;
    private float walkSpeed = 0.2f;

    public GameMode gameMode() {
        return gameMode;
    }
    public SpectorSettings gameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    public boolean canFly() {
        return fly;
    }
    public SpectorSettings canFly(boolean fly) {
        this.fly = fly;
        return this;
    }

    public float flySpeed() {
        return flySpeed;
    }
    public SpectorSettings flySpeed(float speed) {
        this.flySpeed = speed;
        return this;
    }

    public float walkSpeed() {
        return walkSpeed;
    }
    public SpectorSettings walkSpeed(float speed) {
        this.walkSpeed = speed;
        return this;
    }

    public static SpectorSettings read(Player player) {
        return new SpectorSettings()
                .canFly(player.getAllowFlight())
                .flySpeed(player.getFlySpeed())
                .walkSpeed(player.getWalkSpeed())
                .gameMode(player.getGameMode());
    }
    public static void apply(Player player, SpectorSettings settings) {
        player.setGameMode(settings.gameMode());
        player.setAllowFlight(settings.canFly());
        player.setFlying(settings.canFly());
        player.setFlySpeed(settings.flySpeed());
        player.setWalkSpeed(settings.walkSpeed());
    }

    public static SpectorSettings survival() {
        return survival(new SpectorSettings());
    }
    public static SpectorSettings survival(SpectorSettings settings) {
        return settings
                .gameMode(GameMode.SURVIVAL)
                .canFly(false);
    }

    public static SpectorSettings spectator() {
        return spectator(new SpectorSettings());
    }
    public static SpectorSettings spectator(SpectorSettings settings) {
        return settings
                .gameMode(GameMode.ADVENTURE)
                .canFly(true);
    }
}
