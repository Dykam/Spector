package nl.dykam.dev.spector;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class Listeners implements Listener {
    private SpectorManager manager;

    public Listeners(SpectorManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerJoin(PlayerJoinEvent event) {
        manager.add(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerQuit(PlayerQuitEvent event) {
        manager.remove(event.getPlayer());
    }
}
