package nl.dykam.dev.spector;

import org.bukkit.plugin.Plugin;

public class SpectorAPI {
    static SpectorGroupManager manager;
    public static SpectorGroup create(Plugin creator, String name) {
        return manager.create(creator, name);
    }

    static void initialize() {
        manager = new SpectorGroupManager();
    }

    public static SpectorGroupManager getManager() {
        return manager;
    }
}
