package com.ignitedev.igniteLeveling.integration;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.base.stats.Statistic;
import com.ignitedev.igniteLeveling.base.stats.StatisticType;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class LevelingExpansion extends PlaceholderExpansion {

  @Autowired private static LevelingConfiguration configuration;

  private final LevelingPlayerRepository repository;

  /**
   * This method should always return true unless we have a dependency we need to make sure is on
   * the server for our placeholders to work!
   *
   * @return always true since we do not have any dependencies.
   */
  @Override
  public boolean canRegister() {

    return true;
  }

  /**
   * The name of the person who created this expansion should go here.
   *
   * @return The name of the author as a String.
   */
  @Override
  public String getAuthor() {

    return "IgniteDEV";
  }

  /**
   * The placeholder identifier should go here. <br>
   * This is what tells PlaceholderAPI to call our onRequest method to obtain a value if a
   * placeholder starts with our identifier. <br>
   * This must be unique and can not contain % or _
   *
   * @return The identifier in {@code %<identifier>_<value>%} as String.
   */
  @Override
  public String getIdentifier() {

    return "leveling";
  }

  /**
   * This is the version of this expansion. <br>
   * You don't have to use numbers, since it is set as a String.
   *
   * @return The version as a String.
   */
  @Override
  public String getVersion() {

    return "1.0.0";
  }

  /**
   * This is the method called when a placeholder with our identifier is found and needs a value.
   * <br>
   * We specify the value identifier in this method. <br>
   * Since version 2.9.1 can you use OfflinePlayers in your requests.
   *
   * @param player A {@link org.bukkit.OfflinePlayer OfflinePlayer}.
   * @param identifier A String containing the identifier/value.
   * @return Possibly-null String of the requested identifier.
   */

  /*
   * PlaceHolders:
   *
   * %leveling_blocks_broken_level%
   * %leveling_blocks_broken_experience%
   * %leveling_blocks_broken_required_experience%
   * %leveling_blocks_placed_level%
   * %leveling_blocks_placed_experience%
   * %leveling_blocks_placed_required_experience%
   * %leveling_blocks_walked_level%
   * %leveling_blocks_walked_experience%
   * %leveling_blocks_walked_required_experience%
   * %leveling_fished_amount_level%
   * %leveling_fished_amount_experience%
   * %leveling_fished_amount_required_experience%
   * %leveling_mobs_killed_level%
   * %leveling_mobs_killed_experience%
   * %leveling_mobs_killed_required_experience%
   * %leveling_players_killed_level%
   * %leveling_players_killed_experience%
   * %leveling_players_killed_required_experience%
   * %leveling_player_deaths_level%
   * %leveling_player_deaths_experience%
   * %leveling_player_deaths_required_experience%
   * %leveling_playtime_level%
   * %leveling_playtime_experience%
   * %leveling_playtime_required_experience%
   *
   * TOP PlaceHolders:
   *
   * %leveling_top_1_name%
   * %leveling_top_1_blocks_broken_level%
   * %leveling_top_1_blocks_broken_experience%
   * %leveling_top_1_blocks_broken_required_experience%
   * %leveling_top_1_blocks_placed_level%
   * %leveling_top_1_blocks_placed_experience%
   * %leveling_top_1_blocks_placed_required_experience%
   * %leveling_top_1_blocks_walked_level%
   * %leveling_top_1_blocks_walked_experience%
   * %leveling_top_1_blocks_walked_required_experience%
   * %leveling_top_1_fished_amount_level%
   * %leveling_top_1_fished_amount_experience%
   * %leveling_top_1_fished_amount_required_experience%
   * %leveling_top_1_mobs_killed_level%
   * %leveling_top_1_mobs_killed_experience%
   * %leveling_top_1_mobs_killed_required_experience%
   * %leveling_top_1_players_killed_level%
   * %leveling_top_1_players_killed_experience%
   * %leveling_top_1_players_killed_required_experience%
   * %leveling_top_1_player_deaths_level%
   * %leveling_top_1_player_deaths_experience%
   * %leveling_top_1_player_deaths_required_experience%
   * %leveling_top_1_playtime_level%
   * %leveling_top_1_playtime_experience%
   * %leveling_top_1_playtime_required_experience%
   *
   */
  @Override
  public String onRequest(OfflinePlayer player, @NotNull String identifier) {
    LevelingPlayer levelingPlayer = repository.findOrCreateByUUID(player.getUniqueId());
    StatisticType statisticType = StatisticType.getStatisticTypeByName(identifier);

    if (statisticType == null) {
      return null;
    }
    LevelingPlayer topPlayer;

    if (identifier.contains("top")) {
      int topOrder = Integer.parseInt(identifier.replaceAll("[^0-9]", ""));
      topPlayer = repository.getTopPlayer(topOrder - 1, statisticType);

      if (topPlayer == null) {
        return null;
      }
      return processIdentifier(identifier, topPlayer.getStatisticMap().get(statisticType));
    } else {
      return processIdentifier(identifier, levelingPlayer.getStatisticMap().get(statisticType));
    }
  }

  private String processIdentifier(String identifier, Statistic statistic) {
    if (identifier.contains("experience")) {
      return String.valueOf(statistic.getExperience());
    } else if (identifier.contains("required_experience")) {
      return String.valueOf(
          configuration
              .getLevelsRequiredExperience()
              .get(statistic.getStatisticType())
              .get(statistic.getLevel() + 1));
    } else if (identifier.contains("level")) {
      return String.valueOf(statistic.getLevel());
    }
    return null;
  }
}
