package nl.dykam.dev.spector;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
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

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerInteract(PlayerInteractEvent event) {
        SpectorShield shield = getShield(event.getPlayer());
        if(shield != null && !shield.canInteract())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        SpectorShield shield = getShield(event.getPlayer());
        if(shield != null && !shield.canInteract())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBlockBreak(BlockBreakEvent event) {
        SpectorShield shield = getShield(event.getPlayer());
        if(shield != null && !shield.canInteract())
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        /*if (event.getEntity() instanceof Player) {
            SpectorShield shield = manager.getSpector((Player) event.getEntity()).getShield();
            if(shield.isInvincible())
                event.setCancelled(true);
        } else */if (event.getDamager() instanceof Player) {
            SpectorShield shield = getShield((Player) event.getDamager());
            if(shield != null && shield.isPeaceful())
                event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            SpectorShield shield = getShield((Player) entity);
            if(shield != null && shield.isInvincible())
                event.setCancelled(true);
        }
    }

    private SpectorShield getShield(Player entity) {
        Spector spector = manager.getSpector(entity);
        return spector == null ? null : spector.getShield();
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if(event.getTarget() instanceof Player) {
            SpectorShield shield = getShield((Player) event.getTarget());
            if(!shield.isTargetable())
                event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        SpectorShield shield = getShield(event.getPlayer());
        if(shield != null && !shield.canChat()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "Chat is disabled currently disabled for you");
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerPickupItem(PlayerPickupItemEvent event) {
        SpectorShield shield = getShield(event.getPlayer());
        if(shield != null && !shield.canPickup()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerDeath(PlayerDeathEvent event) {
        SpectorShield shield = getShield(event.getEntity());
        if(shield != null && !shield.canDrop())
            event.getDrops().clear();
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        SpectorShield shield = getShield(event.getPlayer());
        if(shield != null && !shield.canDrop())
            event.setCancelled(true);
    }
}
