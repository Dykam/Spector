package nl.dykam.dev.spector;

import org.bukkit.plugin.Plugin;

public class SpectorAPI {
    static SpectorManager manager;
    public static Spector create(Plugin creator, String name) {
        return manager.create(creator, name);
    }

    static void initialize() {
        manager = new SpectorManager();
    }

    public static SpectorManager getManager() {
        return manager;
    }
}
