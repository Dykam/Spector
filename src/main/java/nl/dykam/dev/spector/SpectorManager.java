package nl.dykam.dev.spector;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SpectorManager {
    private final Spector defaultSpector;

    private final Map<SpectorKey, Spector> spectors = new HashMap<>();
    final Map<Player, Spector> spectorMemberships = new HashMap<>();
    private final boolean defaultCanSee;
    private final boolean defaultCanBeSeen;

    public SpectorManager() {
        this(true, true);
    }

    public SpectorManager(boolean defaultCanSee, boolean defaultCanBeSeen) {
        this.defaultCanSee = defaultCanSee;
        this.defaultCanBeSeen = defaultCanBeSeen;
        defaultSpector = new Spector(this, SpectorPlugin.instance(), "default", defaultCanSee);
        spectors.put(defaultSpector, defaultSpector);
    }

    public Spector create(Plugin creator, String name) {
        return create(creator, name, true);
    }

    public Spector create(Plugin creator, String name, boolean canSeeOthersByDefault) {
        Spector spector = new Spector(this, creator, name, canSeeOthersByDefault);
        spectors.put(spector, spector);
        if(defaultCanBeSeen)
            spector.show(defaultSpector);
        else
            spector.hide(defaultSpector);
        return spector;
    }

    public void setSpector(Player player, Spector spector) {
        if(spector == null)
            spector = defaultSpector;
        else if(spector.manager != this)
            throw new IllegalArgumentException("defaultSpector is not owned by this manager!");

        spector.assignTo(player);
    }

    public Spector getDefaultSpector() {
        return defaultSpector;
    }

    public Spector getSpector(Player player) {
        return spectorMemberships.get(player);
    }

    public Spector getSpector(Plugin creator, String name) {
        return spectors.get(new SpectorKey(creator, name));
    }

    public Iterable<Spector> getSpectors() {
        return spectors.values();
    }

    public Iterable<Spector> getSpectors(final Plugin creator) {
        return Iterables.filter(spectors.values(), new Predicate<Spector>() {
            @Override
            public boolean apply(@Nullable Spector spector) {
                return spector.getCreator().equals(creator);
            }
        });
    }

    public Iterable<Spector> getSpectors(final String name) {
        return Iterables.filter(spectors.values(), new Predicate<Spector>() {
            @Override
            public boolean apply(@Nullable Spector spector) {
                return spector.getName().equals(name);
            }
        });
    }

    public void add(Player player) {
        defaultSpector.assignTo(player);
    }

    public void remove(Player player) {
        Spector spector = getSpector(player);
        if(spector == null)
            return;
        spector.members.remove(player);
        spectorMemberships.remove(player);
    }
}
