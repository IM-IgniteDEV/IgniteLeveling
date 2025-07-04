package com.ignitedev.igniteLeveling.config;

import com.ignitedev.igniteLeveling.base.stats.StatisticReward;
import com.ignitedev.igniteLeveling.base.stats.StatisticType;
import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Comment;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@Configuration("leveling.json")
@Getter
@SuppressWarnings("FieldMayBeFinal") // simplejsonconfig is not supporting final fields yet
public class LevelingConfiguration extends Config {

  private String databaseURL = "";
  private String databaseName = "leveling";

  @Comment("If true, plugin checks for AFK players and does not count their actions")
  private boolean afkCheck = true;

  @Comment("Auto save interval in minutes, 0 to disable auto save")
  private int autoSaveInterval = 10;

  @Comment("Auto save message to broadcast, empty to disable")
  private String autoSaveMessage = "&aAuto save completed successfully";

  private Map<StatisticType, Boolean> statisticEnabled =
      Map.of(
          StatisticType.BLOCKS_BROKEN, true,
          StatisticType.BLOCKS_PLACED, true,
          StatisticType.BLOCKS_WALKED, true,
          StatisticType.FISHED_AMOUNT, true,
          StatisticType.MOBS_KILLED, true,
          StatisticType.PLAYERS_KILLED, true,
          StatisticType.PLAYER_DEATHS, true,
          StatisticType.PLAY_TIME, true);

  // Misc Messages

  private String prefix = "&7[&6Leveling&7] &5";

  // Info Messages

  private String confirmationMessage = "Your action has been confirmed";
  private String boosterFinished = "Your booster has finished";
  private String noActiveBooster = "You have no active booster";
  private String boosterApplied =
      "Your booster {MULTIPLIER} has been applied for {DURATION} minutes";
  private String boosterInfo = "Your booster {MULTIPLIER} is active. Time left: {TIME_LEFT}";
  private List<String> checkStatsMessage =
      List.of(
          "===== {PLAYER} Profile =====",
          "Booster: {BOOSTER} (Time Left: {BOOSTER_TIME_LEFT})",
          "Blocks Broken: {BLOCKS_BROKEN_LEVEL} LVL (Experience: {BLOCKS_BROKEN_XP} / {BLOCKS_BROKEN_REQUIRED_XP})",
          "Blocks Placed: {BLOCKS_PLACED_LEVEL} LVL (Experience: {BLOCKS_PLACED_XP} / {BLOCKS_PLACED_REQUIRED_XP})",
          "Fish Caught: {FISHED_AMOUNT_LEVEL} LVL (Experience: {FISHED_AMOUNT_XP} / {FISHED_AMOUNT_REQUIRED_XP})",
          "Mobs Killed: {MOBS_KILLED_LEVEL} LVL (Experience: {MOBS_KILLED_XP} / {MOBS_KILLED_REQUIRED_XP})",
          "Players Killed: {PLAYERS_KILLED_LEVEL} LVL (Experience: {PLAYERS_KILLED_XP} / {PLAYERS_KILLED_REQUIRED_XP})",
          "Deaths: {PLAYER_DEATHS_LEVEL} LVL (Experience: {PLAYER_DEATHS_XP} / {PLAYER_DEATHS_REQUIRED_XP})",
          "Play Time: {PLAY_TIME_LEVEL} LVL (Experience: {PLAY_TIME_XP} / {PLAY_TIME_REQUIRED_XP})");
  private List<String> topPlayersMessage =
      List.of(
          "===== Top Players {STATISTIC_TYPE} =====",
          "1. {TOP_1} - {TOP_1_LEVEL}",
          "2. {TOP_2} - {TOP_2_LEVEL}",
          "3. {TOP_3} - {TOP_3_LEVEL}");
  private List<String> playerTopStatistics =
      List.of(
          "===== {PLAYER} Top Statistics =====",
          "Blocks Broken: {BLOCKS_BROKEN_LEVEL} LVL | Top #{BLOCKS_BROKEN_TOP}",
          "Blocks Placed: {BLOCKS_PLACED_LEVEL} LVL | Top #{BLOCKS_PLACED_TOP}",
          "Fish Caught: {FISHED_AMOUNT_LEVEL} LVL | Top #{FISHED_AMOUNT_TOP}",
          "Mobs Killed: {MOBS_KILLED_LEVEL} LVL | Top #{MOBS_KILLED_TOP}",
          "Players Killed: {PLAYERS_KILLED_LEVEL} LVL | Top #{PLAYERS_KILLED_TOP}",
          "Deaths: {PLAYER_DEATHS_LEVEL} LVL | Top #{PLAYER_DEATHS_TOP}",
          "Play Time: {PLAY_TIME_LEVEL} LVL | Top #{PLAY_TIME_TOP}");

  // Error messages

  private String noPermission = "No permission to use this command";
  private String playerNotFound = "Player not found";
  private String notNumericArgument = "This argument must be numeric";
  private String noTimeSuffix = "Argument must have a time suffix (m (minutes), h (hours))";

  private List<Material> placeBlacklist =
      List.of(
          Material.DIRT,
          Material.GRASS_BLOCK,
          Material.COARSE_DIRT,
          Material.PODZOL,
          Material.MYCELIUM);

  private List<Material> destroyBlacklist =
      List.of(
          Material.DIRT,
          Material.GRASS_BLOCK,
          Material.COARSE_DIRT,
          Material.PODZOL,
          Material.MYCELIUM);

  @Comment("How many experience players gain per action")
  private Map<StatisticType, Integer> statisticExperienceReward =
      Map.of(
          StatisticType.BLOCKS_BROKEN, 1,
          StatisticType.BLOCKS_PLACED, 1,
          StatisticType.BLOCKS_WALKED, 1,
          StatisticType.FISHED_AMOUNT, 10,
          StatisticType.MOBS_KILLED, 20,
          StatisticType.PLAYERS_KILLED, 100,
          StatisticType.PLAYER_DEATHS, 0,
          StatisticType.PLAY_TIME, 50);

  @Comment(
      "How many actions required to gain experience, for example, if you want to get experience for each ten placed block, then set value for BLOCKS_PLACED to 10")
  private Map<StatisticType, Integer> statisticRequiredActions =
      Map.of(
          StatisticType.BLOCKS_BROKEN, 1,
          StatisticType.BLOCKS_PLACED, 50, // every 50 blocks
          StatisticType.BLOCKS_WALKED, 50, // every 50 blocks
          StatisticType.FISHED_AMOUNT, 10, // every 10 fishes
          StatisticType.MOBS_KILLED, 20,
          StatisticType.PLAYERS_KILLED, 100,
          StatisticType.PLAYER_DEATHS, 0,
          StatisticType.PLAY_TIME, 50 // every 50 minutes
          );

  @Comment("How many experience required to gain next level")
  private Map<StatisticType, Map<Integer, Long>> levelsRequiredExperience =
      Map.of(
          StatisticType.BLOCKS_BROKEN,
          Map.of(1, 100L, 2, 500L, 3, 1000L),
          StatisticType.BLOCKS_PLACED,
          Map.of(1, 100L, 2, 500L, 3, 1000L),
          StatisticType.BLOCKS_WALKED,
          Map.of(1, 100L, 2, 500L, 3, 1000L),
          StatisticType.FISHED_AMOUNT,
          Map.of(1, 100L, 2, 500L, 3, 1000L),
          StatisticType.MOBS_KILLED,
          Map.of(1, 100L, 2, 500L, 3, 1000L),
          StatisticType.PLAYERS_KILLED,
          Map.of(1, 100L, 2, 500L, 3, 1000L),
          StatisticType.PLAYER_DEATHS,
          Map.of(1, 100L, 2, 500L, 3, 1000L),
          StatisticType.PLAY_TIME,
          Map.of(1, 100L, 2, 500L, 3, 1000L));

  private Map<Integer, List<StatisticReward>> levelsRewards =
      Map.of(
          1,
          List.of(
              new StatisticReward(
                  List.of(
                      new ItemStack(Material.DIAMOND, 1), new ItemStack(Material.GOLDEN_APPLE, 1)),
                  List.of("broadcast {PLAYER} got first level, well done!"),
                  List.of("say one of that commands {PLAYER}", "say will be execcuted", "say Randomly", "say This is to give you ", "say chance to create random rewards"),
                  List.of("You got first level, well done! {PLAYER}"))),
          2,
          List.of(
              new StatisticReward(
                  List.of(
                      new ItemStack(Material.IRON_CHESTPLATE, 1),
                      new ItemStack(Material.GOLDEN_APPLE, 1)),
                  List.of("broadcast {PLAYER} got second level, well done!"),
                  List.of("say one of that commands {PLAYER}", "say will be execcuted", "say Randomly", "say This is to give you ", "say chance to create random rewards"),
                  List.of("You got second level, well done! {PLAYER}"))),
          3,
          List.of(
              new StatisticReward(
                  List.of(
                      new ItemStack(Material.DIAMOND_SWORD, 1),
                      new ItemStack(Material.GOLDEN_APPLE, 1)),
                  List.of("broadcast {PLAYER} got third level, well done!"),
                  List.of("say one of that commands {PLAYER}", "say will be execcuted", "say Randomly", "say This is to give you ", "say chance to create random rewards"),
                  List.of("You got third level, well done! {PLAYER}"))));
}
