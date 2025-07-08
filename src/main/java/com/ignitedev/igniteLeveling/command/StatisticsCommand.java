package com.ignitedev.igniteLeveling.command;

import com.ignitedev.aparecium.acf.BaseCommand;
import com.ignitedev.aparecium.acf.annotation.*;
import com.ignitedev.aparecium.util.DataUtility;
import com.ignitedev.aparecium.util.MessageUtility;
import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.base.stats.Statistic;
import com.ignitedev.igniteLeveling.base.stats.StatisticType;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@CommandAlias("statistics|stats")
@CommandPermission("igniteleveling.command.statistics")
@RequiredArgsConstructor
public class StatisticsCommand extends BaseCommand {

  @Autowired
  private static LevelingConfiguration configuration;

  private final LevelingPlayerRepository repository;

  @Default
  @CommandPermission("igniteleveling.command.statistics.help")
  @Syntax("<player>")
  public void onStats(Player player, @Optional Player targetPlayer) {
    if (targetPlayer == null) {
      targetPlayer = player;
    }
    LevelingPlayer byUUID = repository.findOrCreateByUUID(targetPlayer.getUniqueId());

    configuration
        .getCheckStatsMessage()
        .forEach(
            message -> {
              Player lambdaPlayer = byUUID.getPlayer();
              String formattedMessage =
                  message
                      .replace("{PLAYER}", lambdaPlayer.getName())
                      .replace("{BOOSTER}", String.valueOf(byUUID.getBoosterMultiplier()))
                      .replace(
                          "{BOOSTER_TIME_LEFT}",
                          DataUtility.convertMinutesToTimeString(byUUID.getBoosterDuration()));

              for (StatisticType value : StatisticType.values()) {
                Statistic statistic = byUUID.getStatisticMap().get(value);

                formattedMessage =
                    formattedMessage
                        .replace(
                            "{" + value.name() + "_LEVEL}", String.valueOf(statistic.getLevel()))
                        .replace(
                            "{" + value.name() + "_XP}", String.valueOf(statistic.getExperience()))
                        .replace(
                            "{" + value.name() + "_REQUIRED_XP}",
                            String.valueOf(
                                configuration
                                    .getLevelsRequiredExperience()
                                    .get(value)
                                    .get(statistic.getLevel() + 1)));
              }
              MessageUtility.send(lambdaPlayer, formattedMessage);
            });
  }

  @Subcommand("top")
  @CommandPermission("igniteleveling.command.statistics.top")
  @Syntax("<statisticType>")
  public void onTop(Player player, StatisticType statisticType) {
    List<String> message = configuration.getTopPlayersMessage();

    for (String string : message) {
      String formattedMessage = string.replace("{STATISTIC_TYPE}", statisticType.name());

      for (int i = 0; i < message.size(); i++) {
        LevelingPlayer topPlayer = repository.getTopPlayer(i, statisticType);

        if (topPlayer == null) {
          formattedMessage = formattedMessage.replace("{TOP_" + (i + 1) + "}", "N/A");
          break;
        }
        String topPlayerName = topPlayer.getOfflinePlayer().getName();

        if (topPlayerName == null) {
          topPlayerName = "N/A";
        }
        formattedMessage =
            formattedMessage
                .replace("{TOP_" + (i + 1) + "}", topPlayerName)
                .replace(
                    "{TOP_" + (i + 1) + "_LEVEL}",
                    String.valueOf(topPlayer.getStatisticMap().get(statisticType).getLevel()));
      }
      MessageUtility.send(player, formattedMessage);
    }
  }

  @Subcommand("top")
  @CommandPermission("igniteleveling.command.statistics.top")
  @Syntax("<targetPlayer (Can be your own name)>")
  public void onTop(Player player, @Optional Player targetPlayer) {
    if (targetPlayer == null) {
      targetPlayer = player;
    }

    List<String> message = configuration.getPlayerTopStatistics();

    for (String string : message) {
      String formattedMessage = string.replace("{PLAYER}", targetPlayer.getName());

      for (StatisticType value : StatisticType.values()) {
        LevelingPlayer byUUID = repository.findOrCreateByUUID(targetPlayer.getUniqueId());
        Statistic statistic = byUUID.getStatisticMap().get(value);

        formattedMessage =
            formattedMessage
                .replace("{" + value.name() + "_LEVEL}", String.valueOf(statistic.getLevel()))
                .replace(
                    "{" + value.name() + "_TOP}",
                    String.valueOf(repository.getPlayerRanking(byUUID, value)));
      }
      MessageUtility.send(player, formattedMessage);
    }
  }
}
