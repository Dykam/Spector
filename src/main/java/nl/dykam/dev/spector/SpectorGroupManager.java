package nl.dykam.dev.spector;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SpectorGroupManager {
    private final SpectorGroup defaultSpector;

    private final Map<SpectorGroupKey, SpectorGroup> groups = new HashMap<>();
    final Map<Player, SpectorGroup> spectorMemberships = new HashMap<>();
    private final boolean defaultCanSee;
    private final boolean defaultCanBeSeen;

    public SpectorGroupManager() {
        this(true, true);
    }

    public SpectorGroupManager(boolean defaultCanSee, boolean defaultCanBeSeen) {
        this.defaultCanSee = defaultCanSee;
        this.defaultCanBeSeen = defaultCanBeSeen;
        defaultSpector = new SpectorGroup(this, SpectorPlugin.instance(), "default", defaultCanSee);
        groups.put(defaultSpector, defaultSpector);
    }

    public SpectorGroup create(Plugin creator, String name) {
        return create(creator, name, true);
    }

    public SpectorGroup create(Plugin creator, String name, boolean canSeeOthersByDefault) {
        SpectorGroup group = new SpectorGroup(this, creator, name, canSeeOthersByDefault);
        groups.put(group, group);
        if(defaultCanBeSeen)
            group.show(defaultSpector);
        else
            group.hide(defaultSpector);
        return group;
    }

    public void setGroup(Player player, SpectorGroup group) {
        if(group == null)
            group = defaultSpector;
        else if(group.manager != this)
            throw new IllegalArgumentException("defaultSpector is not owned by this manager!");

        group.assignTo(player);
    }

    public SpectorGroup getDefaultSpector() {
        return defaultSpector;
    }

    public SpectorGroup getGroup(Player player) {
        return spectorMemberships.get(player);
    }

    public SpectorGroup getGroup(Plugin creator, String name) {
        return groups.get(new SpectorGroupKey(creator, name));
    }

    public Iterable<SpectorGroup> getGroups() {
        return groups.values();
    }

    public Iterable<SpectorGroup> getGroups(final Plugin creator) {
        return Iterables.filter(groups.values(), new Predicate<SpectorGroup>() {
            @Override
            public boolean apply(@Nullable SpectorGroup spectorGroup) {
                return spectorGroup.getCreator().equals(creator);
            }
        });
    }

    public Iterable<SpectorGroup> getGroups(final String name) {
        return Iterables.filter(groups.values(), new Predicate<SpectorGroup>() {
            @Override
            public boolean apply(@Nullable SpectorGroup spectorGroup) {
                return spectorGroup.getName().equals(name);
            }
        });
    }

    public void add(Player player) {
        defaultSpector.assignTo(player);
    }

    public void remove(Player player) {
        SpectorGroup group = getGroup(player);
        if(group == null)
            return;
        group.members.remove(player);
        spectorMemberships.remove(player);
    }
}
