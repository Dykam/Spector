package nl.dykam.dev.spector;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamComponent implements SpectorComponent {
    public static final PotionEffect GHOST_EFFECT = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true);
    private String prefix;
    private TeamMode mode;
    private Scoreboard scoreboard;

    public TeamComponent(Scoreboard scoreboard) {
        this(scoreboard, null);
    }

    public TeamComponent(Scoreboard scoreboard, String prefix) {
        this(scoreboard, prefix, TeamMode.Visible);
    }

    public TeamComponent(Scoreboard scoreboard, String prefix, TeamMode mode) {
        this.prefix = prefix;
        this.mode = mode;
        this.scoreboard = scoreboard;
    }

    @Override
    public void apply(Player player, Spector spector) {
        String creatorName = spector.getCreator().getName();
        String teamName = getTeamName(spector.getName(), creatorName);
        Team currentTeam = scoreboard.getPlayerTeam(player);
        if(currentTeam != null) {
                currentTeam.removePlayer(player);
            if(currentTeam.getPlayers().isEmpty())
                currentTeam.unregister();
        }
        if(mode == TeamMode.Visible || mode == TeamMode.Ghost) {
            Team newTeam = getOrCreateTeam(scoreboard, teamName);
            newTeam.addPlayer(player);
            if(prefix != null)
                newTeam.setPrefix(prefix);
            if(mode == TeamMode.Ghost) {
                newTeam.setCanSeeFriendlyInvisibles(true);
                player.addPotionEffect(GHOST_EFFECT);
            }
        }
    }

    private String getTeamName(String spectorName, String creatorName) {
        int maxCreatorNameLength = 15 - spectorName.length();
        if(maxCreatorNameLength < 4)
            maxCreatorNameLength = 4;
        if(creatorName.length() > maxCreatorNameLength)
            creatorName = creatorName.substring(0, maxCreatorNameLength);
        int maxSpectorNameLength = 15 - creatorName.length();
        if(spectorName.length() > maxSpectorNameLength)
            spectorName = spectorName.substring(0, maxSpectorNameLength);
        return creatorName + ":" + spectorName;
    }

    @Override
    public void clear(Player player, Spector spector) {
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        Team playerTeam = scoreboard.getPlayerTeam(player);
        if(playerTeam != null)
            playerTeam.removePlayer(player);
    }

    private Team getOrCreateTeam(Scoreboard scoreboard, String teamName) {
        Team team = scoreboard.getTeam(teamName);
        if(team == null)
            team = scoreboard.registerNewTeam(teamName);
        return team;
    }
}
