package nl.dykam.dev.spector;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SpectorPlugin extends JavaPlugin {
    private static SpectorPlugin instance;

    public static Plugin instance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        SpectorAPI.initialize();
        Bukkit.getPluginManager().registerEvents(new Listeners(SpectorAPI.getManager()), this);
    }
}
