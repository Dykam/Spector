package nl.dykam.dev.spector;

import org.bukkit.entity.Player;

public interface SpectorComponent {
    void apply(Player player, Spector spector);
    void clear(Player player, Spector spector);
}
