package nl.dykam.dev.spector;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class SpectorGroup extends SpectorGroupKey {
    final SpectorGroupManager manager;
    private boolean canSeeOthersByDefault;
    private final Set<SpectorGroup> offDefault = new HashSet<>();
    final Set<Player> members = new HashSet<>();

    SpectorGroup(SpectorGroupManager manager, Plugin creator, String name) {
        this(manager, creator, name, false);
    }

    SpectorGroup(SpectorGroupManager manager, Plugin creator, String name, boolean canSeeOthersByDefault) {
        super(creator, name);
        this.manager = manager;
        this.canSeeOthersByDefault = canSeeOthersByDefault;
    }

    public void show(SpectorGroup other) {
        if(canSeeOthersByDefault) {
            offDefault.remove(other);
        } else {
            offDefault.add(other);
        }

        Iterable<Player> otherMembers = other.getMembers();
        for (Player player : getMembers()) {
            for (Player otherMember : otherMembers) {
                player.showPlayer(otherMember);
            }
        }
    }

    public void hide(SpectorGroup other) {
        if(canSeeOthersByDefault) {
            offDefault.add(other);
        } else {
            offDefault.remove(other);
        }

        Iterable<Player> otherMembers = other.getMembers();
        for (Player player : getMembers()) {
            for (Player otherMember : otherMembers) {
                player.hidePlayer(otherMember);
            }
        }
    }

    public boolean canSee(SpectorGroup other) {
        return canSeeOthersByDefault != offDefault.contains(other);
    }

    public boolean canSee(Player other) {
        return canSee(manager.getGroup(other));
    }

    public Iterable<Player> getMembers() {
        return members;
    }

    public void assignTo(Player player) {
        SpectorGroup current = manager.getGroup(player);
        if(current != null)
            current.members.remove(player);
        manager.spectorMemberships.put(player, this);

        for (SpectorGroup other : manager.getGroups()) {
            boolean canSeeOther = canSee(other);
            boolean otherCanSee = other.canSee(this);
            for (Player otherPlayer : other.getMembers()) {
                if(canSeeOther)
                    player.showPlayer(otherPlayer);
                else
                    player.hidePlayer(otherPlayer);
                if(otherCanSee)
                    otherPlayer.showPlayer(player);
                else
                    otherPlayer.hidePlayer(player);
            }

        }
    }

    public void unAssignTo(Player player) {
        manager.getDefaultSpector().assignTo(player);
    }
}
