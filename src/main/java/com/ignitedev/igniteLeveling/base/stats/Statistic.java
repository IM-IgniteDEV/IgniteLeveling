package com.ignitedev.igniteLeveling.base.stats;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.event.PlayerLevelUpEvent;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import java.util.Map;
import lombok.Data;
import org.bukkit.Bukkit;

@Data
public class Statistic {

  @Autowired private static LevelingConfiguration configuration;

  private final StatisticType statisticType;
  private final boolean isWorking;

  private int currentProgress = 0;
  private int level = 0;
  private long experience = 0;

  public void incrementExperience(LevelingPlayer player, long experienceToAdd) {
    this.experience = this.experience + experienceToAdd;
    int levelsToGain = 0;

    while (true) {
      Long requiredExperience = getRequiredExperience(this.level + levelsToGain + 1);

      if (requiredExperience == null || this.experience < requiredExperience) {
        break;
      }
      this.experience = this.experience - requiredExperience;
      levelsToGain = levelsToGain + 1;
    }
    if (levelsToGain > 0) {
      levelUp(player, levelsToGain);
    }
  }

  public void incrementProgress(LevelingPlayer player, int progressToAdd) {
    this.currentProgress = this.currentProgress + progressToAdd;
    int requiredActions = getStatisticValue(configuration.getStatisticRequiredActions());

    if (this.currentProgress >= requiredActions) {
      int experienceReward = getStatisticValue(configuration.getStatisticExperienceReward());
      int times = this.currentProgress / requiredActions;

      for (int i = 0; i < times; i++) {
        incrementExperience(player, Math.round(experienceReward * player.getBoosterMultiplier()));
      }
      this.currentProgress = this.currentProgress % requiredActions;
    }
  }

  public void levelUp(LevelingPlayer player, int levels) {
    Bukkit.getPluginManager()
        .callEvent(new PlayerLevelUpEvent(player, this.level, this.level + levels));
    for (int i = 0; i < levels; i++) {
      this.level = this.level + 1;
      configuration
          .getLevelsRewards()
          .get(this.level)
          .forEach(statisticReward -> statisticReward.grantReward(player));
    }
  }

  private Long getRequiredExperience(int level) {
    Map<Integer, Long> experienceMap =
        configuration.getLevelsRequiredExperience().getOrDefault(this.statisticType, Map.of());
    return experienceMap.get(level);
  }

  private int getStatisticValue(Map<StatisticType, Integer> statisticMap) {
    return statisticMap.getOrDefault(this.statisticType, 0);
  }
}
