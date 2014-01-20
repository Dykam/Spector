package nl.dykam.dev.spector;

import org.bukkit.plugin.Plugin;

public class SpectorGroupKey {
    protected final Plugin creator;
    protected final String name;

    public SpectorGroupKey(Plugin creator, String name) {
        this.creator = creator;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Plugin getCreator() {
        return creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpectorGroupKey that = (SpectorGroupKey) o;

        if (!creator.equals(that.creator)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = creator.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
