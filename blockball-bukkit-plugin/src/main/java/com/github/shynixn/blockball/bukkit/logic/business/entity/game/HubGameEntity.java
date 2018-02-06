package com.github.shynixn.blockball.bukkit.logic.business.entity.game;

/*
import com.github.shynixn.blockball.api.persistence.entity.Arena;
import com.github.shynixn.blockball.bukkit.logic.business.configuration.ConfigOld;
import com.github.shynixn.blockball.bukkit.logic.business.configuration.Language;
import com.github.shynixn.blockball.bukkit.logic.business.entity.TemporaryPlayerStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class HubGameEntity extends GameEntity {
    private int timer;

    public HubGameEntity(Arena arena) {
        super(arena);
    }

    @Override
    public synchronized boolean join(Player player, Team team) {
        this.leave(player, false);
        if (team == null) {
            if (this.redTeam.size() > this.blueTeam.size()) {
                team = Team.BLUE;
            } else {
                team = Team.RED;
            }
        }
        if (team == Team.RED && this.redTeam.size() <= this.arena.getTeamMeta().getTeamMaxSize() && (!this.arena.getTeamMeta().isTeamAutoJoin() || this.redTeam.size() <= this.blueTeam.size())) {
            this.storeTemporaryInventory(player);
            player.getInventory().setArmorContents(this.arena.getTeamMeta().getRedItems());
            this.redTeam.add(player);
            player.sendMessage(Language.PREFIX + this.arena.getTeamMeta().getJoinMessage());
            if (this.arena.getTeamMeta().getRedSpawnPoint() != null)
                player.teleport(this.arena.getTeamMeta().getRedSpawnPoint());
            else
                player.teleport(this.arena.getBallSpawnLocation());
            if (this.getHologram() != null) {
                this.getHologram().setText(this.decryptText(this.arena.getTeamMeta().getHologramText()));
                this.getHologram().show(player);
            }
            player.setWalkSpeed(this.arena.getTeamMeta().getWalkingSpeed());
            this.addPlayerToScoreboard(player);
            Bukkit.getPluginManager().callEvent(new GameJoinEvent(this, player));
            return true;
        } else if (team == Team.BLUE && this.blueTeam.size() <= this.arena.getTeamMeta().getTeamMaxSize() && (!this.arena.getTeamMeta().isTeamAutoJoin() || this.blueTeam.size() <= this.redTeam.size())) {
            this.storeTemporaryInventory(player);
            player.getInventory().setArmorContents(this.arena.getTeamMeta().getBlueItems());
            this.blueTeam.add(player);
            player.sendMessage(Language.PREFIX + this.arena.getTeamMeta().getJoinMessage());
            if (this.arena.getTeamMeta().getBlueSpawnPoint() != null)
                player.teleport(this.arena.getTeamMeta().getBlueSpawnPoint());
            else
                player.teleport(this.arena.getBallSpawnLocation());
            if (this.getHologram() != null) {
                this.getHologram().setText(this.decryptText(this.arena.getTeamMeta().getHologramText()));
                this.getHologram().show(player);
            }
            player.setWalkSpeed(this.arena.getTeamMeta().getWalkingSpeed());
            this.addPlayerToScoreboard(player);
            Bukkit.getPluginManager().callEvent(new GameJoinEvent(this, player));
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        if (!this.arena.isEnabled())
            return;
        this.timer--;
        if (this.timer <= 0) {

            this.updateSigns();
            this.fixCachedRangePlayers();
            this.timer = 20;
        }
        super.run();
    }

    private void updateSigns() {
        Location[] locations = this.arena.getLobbyMeta().getRedTeamSignLocations().toArray(new Location[this.arena.getLobbyMeta().getRedTeamSignLocations().size()]);
        for (int i = 0; i < locations.length; i++) {
            final Location location = locations[i];
            if (location.getBlock().getType() == Material.SIGN_POST || location.getBlock().getType() == Material.WALL_SIGN) {
                ConfigOld.getInstance().getTeamSign().updateTeamSignConsideringMaxPlayers((Sign) location.getBlock().getState(), this, Team.RED, this.redTeam);
            } else {
                this.arena.getLobbyMeta().removeRedTeamSignLocation(i);
            }
        }
        locations = this.arena.getLobbyMeta().getBlueTeamSignLocations().toArray(new Location[this.arena.getLobbyMeta().getBlueTeamSignLocations().size()]);
        for (int i = 0; i < locations.length; i++) {
            final Location location = locations[i];
            if (location.getBlock().getType() == Material.SIGN_POST || location.getBlock().getType() == Material.WALL_SIGN) {
                ConfigOld.getInstance().getTeamSign().updateTeamSignConsideringMaxPlayers((Sign) location.getBlock().getState(), this, Team.BLUE, this.blueTeam);
            } else {
                this.arena.getLobbyMeta().removeBlueTeamSignLocation(i);
            }
        }
    }

    private void storeTemporaryInventory(Player player) {
        final TemporaryPlayerStorage storage = new TemporaryPlayerStorage();
        storage.armorContent = player.getInventory().getArmorContents().clone();
        storage.isFlying = player.getAllowFlight();
        storage.walkingSpeed = player.getWalkSpeed();
        storage.scoreboard = player.getScoreboard();
        this.temporaryStorage.put(player, storage);
    }

    private boolean isCustomDrop(Entity entity) {
        for (final Item item : this.arena.getBoostItemHandler().getDroppedItems()) {
            if (item.equals(entity))
                return true;
        }
        return false;
    }
}
*/