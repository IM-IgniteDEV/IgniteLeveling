package com.ignitedev.igniteLeveling.base.stats;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.event.PlayerGainExperienceEvent;
import com.ignitedev.igniteLeveling.event.PlayerGainProgressEvent;
import com.ignitedev.igniteLeveling.event.PlayerLevelUpEvent;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;

import java.util.Map;

import lombok.Data;
import org.bukkit.Bukkit;

/**
 * Represents a statistic in the leveling system. Handles experience, progress, and level management
 * for a player.
 */
@Data
public class Statistic {

  @Autowired
  private static LevelingConfiguration configuration;

  private final StatisticType statisticType; // Type of the statistic.
  private final boolean isWorking; // Indicates if the statistic is active.

  private int currentProgress = 0; // Current progress towards the next reward.
  private int level = 0; // Current level of the statistic.
  private long experience = 0; // Total experience accumulated.

  /**
   * Adds experience to the player and checks if they level up.
   *
   * @param player          The player whose experience is being incremented.
   * @param experienceToAdd The amount of experience to add.
   */
  public void incrementExperience(LevelingPlayer player, long experienceToAdd) {
    long oldExperience = this.experience;
    this.experience = this.experience + experienceToAdd;
    int levelsToGain = 0;

    // Check if the player can level up based on their experience.
    while (true) {
      Long requiredExperience = getRequiredExperience(this.level + levelsToGain + 1);

      if (requiredExperience == null || this.experience < requiredExperience) {
        break;
      }
      this.experience = this.experience - requiredExperience;
      levelsToGain = levelsToGain + 1;
    }

    // Level up the player if they gained any levels.
    if (levelsToGain > 0) {
      levelUp(player, levelsToGain);
    }

    // Trigger an event for experience gain.
    Bukkit.getPluginManager()
        .callEvent(
            new PlayerGainExperienceEvent(
                player, oldExperience, this.experience, levelsToGain > 0, this.level));
  }

  /**
   * Adds progress to the player and grants rewards if thresholds are met.
   *
   * @param player        The player whose progress is being incremented.
   * @param progressToAdd The amount of progress to add.
   */
  public void incrementProgress(LevelingPlayer player, int progressToAdd) {
    int oldProgress = this.currentProgress;
    this.currentProgress = this.currentProgress + progressToAdd;
    int requiredActions = getStatisticValue(configuration.getStatisticRequiredActions());

    // Check if the player has reached the required actions threshold.
    if (this.currentProgress >= requiredActions) {
      int experienceReward = getStatisticValue(configuration.getStatisticExperienceReward());
      int times = this.currentProgress / requiredActions;

      // Grant experience rewards for each threshold crossed.
      for (int i = 0; i < times; i++) {
        incrementExperience(player, Math.round(experienceReward * player.getBoosterMultiplier()));
      }

      // Update the remaining progress after rewards.
      this.currentProgress = this.currentProgress % requiredActions;

      // Trigger an event for progress gain.
      Bukkit.getPluginManager()
          .callEvent(
              new PlayerGainProgressEvent(
                  player, oldProgress, this.currentProgress, times > 0, this.experience));
    }
  }

  /**
   * Levels up the player and grants rewards for each level gained.
   *
   * @param player The player who is leveling up.
   * @param levels The number of levels to gain.
   */
  public void levelUp(LevelingPlayer player, int levels) {
    // Trigger an event for leveling up.
    Bukkit.getPluginManager()
        .callEvent(new PlayerLevelUpEvent(player, this.level, this.level + levels));

    // Increment the player's level and grant rewards for each new level.
    for (int i = 0; i < levels; i++) {
      this.level = this.level + 1;
      configuration
          .getLevelsRewards()
          .get(this.level)
          .forEach(statisticReward -> statisticReward.grantReward(player));
    }
  }

  /**
   * Retrieves the required experience for a given level.
   *
   * @param level The level for which required experience is needed.
   * @return The required experience for the level, or null if not defined.
   */
  private Long getRequiredExperience(int level) {
    Map<Integer, Long> experienceMap =
        configuration.getLevelsRequiredExperience().getOrDefault(this.statisticType, Map.of());
    return experienceMap.get(level);
  }

  /**
   * Retrieves the value of a statistic from a map.
   *
   * @param statisticMap The map containing statistic values.
   * @return The value of the statistic, or 0 if not defined.
   */
  private int getStatisticValue(Map<StatisticType, Integer> statisticMap) {
    return statisticMap.getOrDefault(this.statisticType, 0);
  }
}
