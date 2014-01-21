package nl.dykam.dev.spector;

import org.bukkit.plugin.Plugin;

public class SpectorAPI {
    static SpectorManager manager;
    public static Spector create(Plugin creator, String name) {
        return getManager().create(creator, name);
    }

    static void initialize() {
        manager = new SpectorManager();
    }

    public static SpectorManager getManager() {
        if(manager == null)
            throw new IllegalStateException("SpectorPlugin has not been initialized yet. Make sure Spector is loaded before any dependency.");
        return manager;
    }
}
