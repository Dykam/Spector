package nl.dykam.dev.spector;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class Listeners implements Listener {
    private SpectorGroupManager manager;

    public Listeners(SpectorGroupManager manager) {
        this.manager = manager;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        manager.add(event.getPlayer());
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        manager.remove(event.getPlayer());
    }
}
