package com.ignitedev.igniteLeveling.base.stats;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.Data;
import org.bukkit.entity.Player;

@Data
public class Statistic {

  @Autowired private static LevelingConfiguration configuration;

  private final StatisticType statisticType;

  private final boolean isWorking;

  private int currentProgress = 0;
  private int level = 0;
  private long experience = 0;

  public void incrementExperience(Player player, long experienceToAdd) {
    this.experience = this.experience + experienceToAdd;

    long requiredExperience =
        configuration.getLevelsRequiredExperience().get(this.statisticType).get(this.level + 1);

    if (this.experience >= requiredExperience) {
      this.experience = this.experience - requiredExperience;
      // we pass surplus of experience to the next level
      levelUp(player);
    }
  }

  public void incrementProgress(LevelingPlayer player, int progressToAdd) {
    this.currentProgress = this.currentProgress + progressToAdd;

    int requiredActions = configuration.getStatisticRequiredActions().get(this.statisticType);

    if (this.currentProgress % requiredActions == 0) {
      int experienceReward = configuration.getStatisticExperienceReward().get(this.statisticType);

      incrementExperience(
          player.getPlayer(), Math.round(experienceReward * player.getBoosterMultiplier()));
      this.currentProgress = 0;
    }
  }

  public void levelUp(Player player) {
    this.level = this.level + 1;
    configuration
        .getLevelsRewards()
        .get(this.level)
        .forEach(statisticReward -> statisticReward.grantReward(player));
  }
}
