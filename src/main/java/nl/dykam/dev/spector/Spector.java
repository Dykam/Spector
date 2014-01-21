package nl.dykam.dev.spector;

import org.apache.commons.lang.NullArgumentException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class Spector extends SpectorKey {
    final SpectorManager manager;
    private boolean canSeeOthersByDefault;
    private final Set<Spector> offDefault = new HashSet<>();
    final Set<Player> members = new HashSet<>();
    private SpectorShield shield = new SpectorShield();
    private SpectorSettings settings = new SpectorSettings();
    private Set<SpectorComponent> components;

    Spector(SpectorManager manager, Plugin creator, String name) {
        this(manager, creator, name, false);
    }

    Spector(SpectorManager manager, Plugin creator, String name, boolean canSeeOthersByDefault) {
        super(creator, name);
        this.manager = manager;
        this.canSeeOthersByDefault = canSeeOthersByDefault;
        components = new HashSet<>();
    }

    public void show(Spector other) {
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

    private void showTo(Spector spector) {
        spector.show(this);
    }

    public void showAll() {
        for (Spector spector : manager.getSpectors()) {
            show(spector);
        }
    }

    public void showToAll() {
        for (Spector spector : manager.getSpectors()) {
            showTo(spector);
        }
    }

    public void hide(Spector other) {
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

    private void hideFor(Spector spector) {
        spector.hide(this);
    }

    public void hideAll() {
        for (Spector spector : manager.getSpectors()) {
            hide(spector);
        }
    }

    public void hideForAll() {
        for (Spector spector : manager.getSpectors()) {
            hideFor(spector);
        }
    }

    public boolean canSee(Spector other) {
        return canSeeOthersByDefault != offDefault.contains(other);
    }

    public boolean canSee(Player other) {
        return canSee(manager.getSpector(other));
    }

    public void addComponent(SpectorComponent component) {
        components.add(component);
        for (Player player : getMembers()) {
            component.apply(player, this);
        }
    }

    public void removeComponent(SpectorComponent component) {
        if(!components.remove(component))
            return;
        for (Player player : getMembers()) {
            component.clear(player, this);
        }
    }

    public Iterable<Player> getMembers() {
        return members;
    }

    public SpectorShield getShield() {
        return shield;
    }

    public void setShield(SpectorShield shield) {
        if(shield == null)
            throw new NullArgumentException("shield");
        this.shield = shield;
    }

    public SpectorSettings getSettings() {
        return settings;
    }

    public void setSettings(SpectorSettings settings) {
        this.settings = settings;
    }

    public void assignTo(Player player) {
        Spector current = manager.getSpector(player);
        if(current != null) {
            current.members.remove(player);
            for (SpectorComponent component : current.components) {
                component.clear(player, this);
            }
        }
        manager.spectorMemberships.put(player, this);
        members.add(player);
        if(settings != null)
            SpectorSettings.apply(player, settings);
        for (SpectorComponent component : components) {
            component.apply(player, this);
        }


        for (Spector other : manager.getSpectors()) {
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

    @Override
    public String toString() {
        return "Spector{" + creator +":" + name + "'}";
    }
}
