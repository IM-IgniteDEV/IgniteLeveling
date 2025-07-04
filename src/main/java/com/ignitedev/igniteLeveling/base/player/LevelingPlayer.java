package com.ignitedev.igniteLeveling.base.player;

import com.ignitedev.aparecium.util.MessageUtility;
import com.ignitedev.igniteLeveling.IgniteLeveling;
import com.ignitedev.igniteLeveling.base.stats.Statistic;
import com.ignitedev.igniteLeveling.base.stats.StatisticType;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.task.BoosterTimeTask;
import com.ignitedev.igniteLeveling.task.TrackPlayerTimeTask;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class LevelingPlayer {

  @Autowired private static LevelingConfiguration configuration;

  // * Base data

  private final UUID uuid;

  // * Boosters

  private int boosterDuration = 0; // Duration in minutes
  private double boosterMultiplier = 1.0;

  // * Statistics

  private Map<StatisticType, Statistic> statisticMap = new HashMap<>();

  private transient BoosterTimeTask boosterTimeTask;
  private transient TrackPlayerTimeTask trackPlayerTimeTask;
  private transient Location lastKnownLocation;

  public LevelingPlayer(UUID uuid) {
    this.uuid = uuid;
    Arrays.stream(StatisticType.values())
        .forEach(
            statisticType ->
                statisticMap.put(
                    statisticType,
                    new Statistic(
                        statisticType, configuration.getStatisticEnabled().get(statisticType))));
  }

  public Player getPlayer() {
    return Bukkit.getPlayer(uuid);
  }

  public OfflinePlayer getOfflinePlayer() {
    return Bukkit.getOfflinePlayer(uuid);
  }

  public void grantBooster(IgniteLeveling plugin, int duration, double multiplier) {
    MessageUtility.send(
        getPlayer(),
        configuration.getPrefix()
            + configuration
                .getBoosterApplied()
                .replace("{DURATION}", String.valueOf(duration))
                .replace("{MULTIPLIER}", String.valueOf(multiplier)));

    this.boosterDuration = this.boosterDuration + duration;
    this.boosterMultiplier = this.boosterMultiplier * multiplier;

    this.boosterTimeTask = new BoosterTimeTask(this);
    this.boosterTimeTask.runTaskTimer(plugin, 0, 20L * 60);
  }

  public void revokeBooster() {
    this.boosterDuration = 0;
    this.boosterMultiplier = 1.0;

    if (this.boosterTimeTask != null) {
      this.boosterTimeTask.cancel();
      this.boosterTimeTask = null;
    }
  }
}
