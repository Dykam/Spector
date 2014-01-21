package nl.dykam.dev.spector;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class ShieldListener implements Listener {
    private final SpectorManager manager;

    public ShieldListener(SpectorManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerInteract(PlayerInteractEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canInteract())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canInteract())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onBlockBreak(BlockBreakEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canInteract())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        /*if (event.getEntity() instanceof Player) {
            SpectorShield shield = manager.getSpector((Player) event.getEntity()).getShield();
            if(shield.isInvincible())
                event.setCancelled(true);
        } else */if (event.getDamager() instanceof Player) {
            SpectorShield shield = manager.getSpector((Player) event.getDamager()).getShield();
            if(shield.isPeaceful())
                event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            SpectorShield shield = manager.getSpector((Player) event.getEntity()).getShield();
            if(shield.isInvincible())
                event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if(event.getTarget() instanceof Player) {
            SpectorShield shield = manager.getSpector((Player) event.getTarget()).getShield();
            if(!shield.isTargetable())
                event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canChat()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Chat is disabled currently disabled for you");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerPickupItem(PlayerPickupItemEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canPickup()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerDeath(PlayerDeathEvent event) {
        SpectorShield shield = manager.getSpector(event.getEntity()).getShield();
        if(!shield.canDrop())
            event.getDrops().clear();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        SpectorShield shield = manager.getSpector(event.getPlayer()).getShield();
        if(!shield.canDrop())
            event.setCancelled(true);
    }
}
