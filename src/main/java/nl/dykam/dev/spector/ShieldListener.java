package nl.dykam.dev.spector;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ShieldListener implements Listener {
    private final SpectorManager manager;

    public ShieldListener(SpectorManager manager) {
        this.manager = manager;
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canInteract())
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canInteract())
            event.setCancelled(true);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canInteract())
            event.setCancelled(true);
    }

    @EventHandler
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            SpectorShield shield = manager.getSpector((Player) event.getEntity()).getShield();
            if(shield.isInvincible())
                event.setCancelled(true);
        } else if (event.getDamager() instanceof Player) {
            SpectorShield shield = manager.getSpector((Player) event.getDamager()).getShield();
            if(shield.isPeaceful())
                event.setCancelled(true);
        }
    }

    @EventHandler
    private void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if(event.getTarget() instanceof Player) {
            SpectorShield shield = manager.getSpector((Player) event.getTarget()).getShield();
            if(!shield.isTargetable())
                event.setCancelled(true);
        }
    }

    @EventHandler
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canChat())
            event.setCancelled(true);
    }

    @EventHandler
    private void onAsyncPlayerChat(PlayerPickupItemEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canPickup())
            event.setCancelled(true);
    }
}
