package com.ignitedev.igniteLeveling.task;

import static com.ignitedev.igniteLeveling.base.stats.StatisticType.PLAY_TIME;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class TrackPlayerTimeTask extends BukkitRunnable {

  @Autowired private static LevelingConfiguration configuration;

  private final LevelingPlayer levelingPlayer;

  @Override
  public void run() {
    Player player = this.levelingPlayer.getPlayer();

    if (player == null || !player.isOnline()) {
      levelingPlayer.setTrackPlayerTimeTask(null);
      this.cancel();
      return;
    }
    Location playerLocation = player.getLocation();

    if (this.levelingPlayer.getLastKnownLocation() == null) {
      this.levelingPlayer.setLastKnownLocation(playerLocation);
    }
    if (configuration.isAfkCheck()) {
      if (this.levelingPlayer.getLastKnownLocation().distanceSquared(playerLocation) < 1) {
        // player has not moved // probably is afk
        return;
      }
    }
    this.levelingPlayer.getStatisticMap().get(PLAY_TIME).incrementProgress(this.levelingPlayer, 1);
    this.levelingPlayer.setLastKnownLocation(playerLocation);
  }
}
