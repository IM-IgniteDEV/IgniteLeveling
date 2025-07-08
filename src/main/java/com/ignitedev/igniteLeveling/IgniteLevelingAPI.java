package com.ignitedev.igniteLeveling;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.base.stats.Statistic;
import com.ignitedev.igniteLeveling.base.stats.StatisticType;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;

import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Public API for interacting with the leveling, statistics, and boosters system.
 */
@RequiredArgsConstructor
@Getter
public class IgniteLevelingAPI {

  private static IgniteLevelingAPI INSTANCE;

  private final LevelingPlayerRepository playerRepository;

  public static void init(@NotNull LevelingPlayerRepository playerRepository) {
    if (INSTANCE == null) {
      INSTANCE = new IgniteLevelingAPI(playerRepository);
    }
  }

  /**
   * Returns the singleton instance.
   */
  public static IgniteLevelingAPI getInstance() {
    if (INSTANCE == null) {
      throw new IllegalStateException("IgniteLevelingAPI is not initialized!");
    }
    return INSTANCE;
  }

  // --- Player management ---

  /**
   * Gets or creates a player by UUID.
   *
   * @param uuid the player's UUID
   * @return the LevelingPlayer object (never null)
   */
  @NotNull
  public LevelingPlayer getOrCreatePlayer(@NotNull UUID uuid) {
    return playerRepository.findOrCreateByUUID(uuid);
  }

  // --- Statistics management ---

  /**
   * Gets the Statistic object for a player and type.
   *
   * @param player the player (not null)
   * @param type   the statistic type (not null)
   * @return the Statistic object or null if not found
   */
  @Nullable
  public Statistic getStatistic(@NotNull LevelingPlayer player, @NotNull StatisticType type) {
    return player.getStatisticMap().get(type);
  }

  /**
   * Gets the level of a player's statistic.
   *
   * @param player the player (not null)
   * @param type   the statistic type (not null)
   * @return the level, or 0 if not found
   */
  public int getStatisticLevel(@NotNull LevelingPlayer player, @NotNull StatisticType type) {
    Statistic stat = getStatistic(player, type);
    return stat != null ? stat.getLevel() : 0;
  }

  /**
   * Gets the experience of a player's statistic.
   *
   * @param player the player (not null)
   * @param type   the statistic type (not null)
   * @return the experience value, or 0 if not found
   */
  public long getStatisticExperience(@NotNull LevelingPlayer player, @NotNull StatisticType type) {
    Statistic stat = getStatistic(player, type);
    return stat != null ? stat.getExperience() : 0;
  }

  /**
   * Gets the progress of a player's statistic.
   *
   * @param player the player (not null)
   * @param type   the statistic type (not null)
   * @return the progress value, or 0 if not found
   */
  public int getStatisticProgress(@NotNull LevelingPlayer player, @NotNull StatisticType type) {
    Statistic stat = getStatistic(player, type);
    return stat != null ? stat.getCurrentProgress() : 0;
  }

  /**
   * Increments the experience of a player's statistic.
   *
   * @param player the player (not null)
   * @param type   the statistic type (not null)
   * @param exp    the amount of experience to add
   * @param plugin the IgniteLeveling plugin instance (not null)
   */
  public void incrementStatisticExperience(
      @NotNull LevelingPlayer player,
      @NotNull StatisticType type,
      long exp,
      @NotNull IgniteLeveling plugin) {
    Statistic stat = getStatistic(player, type);
    if (stat != null) {
      stat.incrementExperience(player, exp);
    }
  }

  /**
   * Increments the progress of a player's statistic.
   *
   * @param player   the player (not null)
   * @param type     the statistic type (not null)
   * @param progress the amount of progress to add
   * @param plugin   the IgniteLeveling plugin instance (not null)
   */
  public void incrementStatisticProgress(
      @NotNull LevelingPlayer player,
      @NotNull StatisticType type,
      int progress,
      @NotNull IgniteLeveling plugin) {
    Statistic stat = getStatistic(player, type);
    if (stat != null) {
      stat.incrementProgress(player, progress);
    }
  }

  // --- Boosters management ---

  /**
   * Grants a booster to the player.
   *
   * @param player     the player (not null)
   * @param plugin     the IgniteLeveling plugin instance (not null)
   * @param duration   booster duration in minutes
   * @param multiplier booster multiplier
   */
  public void grantBooster(
      @NotNull LevelingPlayer player,
      @NotNull IgniteLeveling plugin,
      int duration,
      double multiplier) {
    player.grantBooster(plugin, duration, multiplier);
  }

  /**
   * Revokes the player's booster.
   *
   * @param player the player (not null)
   */
  public void revokeBooster(@NotNull LevelingPlayer player) {
    player.revokeBooster();
  }

  /**
   * Gets the remaining booster duration for the player.
   *
   * @param player the player (not null)
   * @return booster duration in minutes
   */
  public int getBoosterDuration(@NotNull LevelingPlayer player) {
    return player.getBoosterDuration();
  }

  /**
   * Gets the booster multiplier for the player.
   *
   * @param player the player (not null)
   * @return booster multiplier
   */
  public double getBoosterMultiplier(@NotNull LevelingPlayer player) {
    return player.getBoosterMultiplier();
  }
}
