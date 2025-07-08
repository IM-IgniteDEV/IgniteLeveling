# IgniteLeveling

IgniteLeveling is a plugin that introduces a comprehensive player leveling and statistics system. It tracks various
player activities, rewards progress, and supports boosters and leaderboards.


---

## Features

- Tracks player statistics such as blocks broken/placed, mobs killed, play time, and more
- Leveling system with configurable experience and rewards
- Booster system to temporarily increase experience gain
- Leaderboards and top player commands
- PlaceholderAPI integration for custom placeholders
- MongoDB support for persistent data storage
- Highly configurable via `leveling.json`

---

## Requirements

- Java 21+
- MongoDB
- PlaceholderAPI (for placeholders)
- Spigot 1.8.8 - 1.21.4

---

## Commands

- `/booster` — View or manage boosters  
  **Permission:** `igniteleveling.command.booster`


- `/booster activate <player> <duration> <multiplier>` — Activate a booster for a player  
  **Permission:** `igniteleveling.command.booster.activate`


- `/booster revoke <player>` — Revoke a player's booster  
  **Permission:** `igniteleveling.command.booster.revoke`


- `/statistics` or `/stats` — View your statistics  
  **Permission:** `igniteleveling.command.statistics.help`


- `/statistics <player>` — View another player's statistics  
  **Permission:** `igniteleveling.command.statistics.help`


- `/statistics top <statisticType>` — Show top players for a statistic  
  **Permission:** `igniteleveling.command.statistics.top`


- `/statistics top <player>` — Show a player's top rankings  
  **Permission:** `igniteleveling.command.statistics.top`

---

## Configuration

```jsonc
{
  "databaseURL": "", // MongoDB database URL
  "databaseName": "leveling", // Database name

  "afkCheck": true, // If true, plugin checks for AFK players and does not count their actions
  "autoSaveInterval": 10, // Auto save interval in minutes, 0 to disable auto save
  "autoSaveMessage": "&aAuto save completed successfully", // Auto save message to broadcast, empty to disable

  "statisticEnabled": {
    "BLOCKS_BROKEN": true,
    "BLOCKS_PLACED": true,
    "BLOCKS_WALKED": true,
    "FISHED_AMOUNT": true,
    "MOBS_KILLED": true,
    "PLAYERS_KILLED": true,
    "PLAYER_DEATHS": true,
    "PLAY_TIME": true
  },

  "prefix": "&7[&6Leveling&7] &5", // Plugin message prefix
  "confirmationMessage": "Your action has been confirmed",
  "boosterFinished": "Your booster has finished",
  "noActiveBooster": "You have no active booster",
  "boosterApplied": "Your booster {MULTIPLIER} has been applied for {DURATION} minutes",
  "boosterInfo": "Your booster {MULTIPLIER} is active. Time left: {TIME_LEFT}",

  "checkStatsMessage": [
    "===== {PLAYER} Profile =====",
    "Booster: {BOOSTER} (Time Left: {BOOSTER_TIME_LEFT})",
    "Blocks Broken: {BLOCKS_BROKEN_LEVEL} LVL (Experience: {BLOCKS_BROKEN_XP} / {BLOCKS_BROKEN_REQUIRED_XP})",
    "Blocks Placed: {BLOCKS_PLACED_LEVEL} LVL (Experience: {BLOCKS_PLACED_XP} / {BLOCKS_PLACED_REQUIRED_XP})",
    "Fish Caught: {FISHED_AMOUNT_LEVEL} LVL (Experience: {FISHED_AMOUNT_XP} / {FISHED_AMOUNT_REQUIRED_XP})",
    "Mobs Killed: {MOBS_KILLED_LEVEL} LVL (Experience: {MOBS_KILLED_XP} / {MOBS_KILLED_REQUIRED_XP})",
    "Players Killed: {PLAYERS_KILLED_LEVEL} LVL (Experience: {PLAYERS_KILLED_XP} / {PLAYERS_KILLED_REQUIRED_XP})",
    "Deaths: {PLAYER_DEATHS_LEVEL} LVL (Experience: {PLAYER_DEATHS_XP} / {PLAYER_DEATHS_REQUIRED_XP})",
    "Play Time: {PLAY_TIME_LEVEL} LVL (Experience: {PLAY_TIME_XP} / {PLAY_TIME_REQUIRED_XP})"
  ],
  "topPlayersMessage": [
    "===== Top Players {STATISTIC_TYPE} =====",
    "1. {TOP_1} - {TOP_1_LEVEL}",
    "2. {TOP_2} - {TOP_2_LEVEL}",
    "3. {TOP_3} - {TOP_3_LEVEL}"
  ],
  "playerTopStatistics": [
    "===== {PLAYER} Top Statistics =====",
    "Blocks Broken: {BLOCKS_BROKEN_LEVEL} LVL | Top #{BLOCKS_BROKEN_TOP}",
    "Blocks Placed: {BLOCKS_PLACED_LEVEL} LVL | Top #{BLOCKS_PLACED_TOP}",
    "Fish Caught: {FISHED_AMOUNT_LEVEL} LVL | Top #{FISHED_AMOUNT_TOP}",
    "Mobs Killed: {MOBS_KILLED_LEVEL} LVL | Top #{MOBS_KILLED_TOP}",
    "Players Killed: {PLAYERS_KILLED_LEVEL} LVL | Top #{PLAYERS_KILLED_TOP}",
    "Deaths: {PLAYER_DEATHS_LEVEL} LVL | Top #{PLAYER_DEATHS_TOP}",
    "Play Time: {PLAY_TIME_LEVEL} LVL | Top #{PLAY_TIME_TOP}"
  ],

  "noPermission": "No permission to use this command",
  "playerNotFound": "Player not found",
  "notNumericArgument": "This argument must be numeric",
  "noTimeSuffix": "Argument must have a time suffix (m (minutes), h (hours))",

  "placeBlacklist": [
    "DIRT",
    "GRASS_BLOCK",
    "COARSE_DIRT",
    "PODZOL",
    "MYCELIUM"
  ],
  "destroyBlacklist": [
    "DIRT",
    "GRASS_BLOCK",
    "COARSE_DIRT",
    "PODZOL",
    "MYCELIUM"
  ],

  "statisticExperienceReward": { // How much experience players gain per action
    "BLOCKS_BROKEN": 1,
    "BLOCKS_PLACED": 1,
    "BLOCKS_WALKED": 1,
    "FISHED_AMOUNT": 10,
    "MOBS_KILLED": 20,
    "PLAYERS_KILLED": 100,
    "PLAYER_DEATHS": 0,
    "PLAY_TIME": 50
  },

  "statisticRequiredActions": { // How many actions are required to gain experience (e.g. 10 for every 10 placed blocks)
    "BLOCKS_BROKEN": 1,
    "BLOCKS_PLACED": 50,
    "BLOCKS_WALKED": 50,
    "FISHED_AMOUNT": 10,
    "MOBS_KILLED": 20,
    "PLAYERS_KILLED": 100,
    "PLAYER_DEATHS": 0,
    "PLAY_TIME": 50
  },

  "levelsRequiredExperience": { // How much experience is required for each level
    "BLOCKS_BROKEN": { "1": 100, "2": 500, "3": 1000 },
    "BLOCKS_PLACED": { "1": 100, "2": 500, "3": 1000 },
    "BLOCKS_WALKED": { "1": 100, "2": 500, "3": 1000 },
    "FISHED_AMOUNT": { "1": 100, "2": 500, "3": 1000 },
    "MOBS_KILLED": { "1": 100, "2": 500, "3": 1000 },
    "PLAYERS_KILLED": { "1": 100, "2": 500, "3": 1000 },
    "PLAYER_DEATHS": { "1": 100, "2": 500, "3": 1000 },
    "PLAY_TIME": { "1": 100, "2": 500, "3": 1000 }
  },

  "levelsRewards": {
    "1": [
      {
        "items": [
          {
            "==": "org.bukkit.inventory.ItemStack",
            "v": 2230,
            "type": "DIAMOND",
            "amount": 1
          },
          {
            "==": "org.bukkit.inventory.ItemStack",
            "v": 2230,
            "type": "GOLDEN_APPLE",
            "amount": 1
          }
        ],
        "broadcastCommands": [
          "broadcast {PLAYER} got first level, well done!"
        ],
        "randomCommands": [
          "say one of that commands {PLAYER}",
          "say will be execcuted",
          "say Randomly",
          "say This is to give you ",
          "say chance to create random rewards"
        ],
        "messages": [
          "You got first level, well done! {PLAYER}"
        ]
      }
    ],
    "2": [
      {
        "items": [
          {
            "==": "org.bukkit.inventory.ItemStack",
            "v": 2230,
            "type": "IRON_CHESTPLATE",
            "amount": 1
          },
          {
            "==": "org.bukkit.inventory.ItemStack",
            "v": 2230,
            "type": "GOLDEN_APPLE",
            "amount": 1
          }
        ],
        "broadcastCommands": [
          "broadcast {PLAYER} got second level, well done!"
        ],
        "randomCommands": [
          "say one of that commands {PLAYER}",
          "say will be execcuted",
          "say Randomly",
          "say This is to give you ",
          "say chance to create random rewards"
        ],
        "messages": [
          "You got second level, well done! {PLAYER}"
        ]
      }
    ],
    "3": [
      {
        "items": [
          {
            "==": "org.bukkit.inventory.ItemStack",
            "v": 2230,
            "type": "DIAMOND_SWORD",
            "amount": 1
          },
          {
            "==": "org.bukkit.inventory.ItemStack",
            "v": 2230,
            "type": "GOLDEN_APPLE",
            "amount": 1
          }
        ],
        "broadcastCommands": [
          "broadcast {PLAYER} got third level, well done!"
        ],
        "randomCommands": [
          "say one of that commands {PLAYER}",
          "say will be execcuted",
          "say Randomly",
          "say This is to give you ",
          "say chance to create random rewards"
        ],
        "messages": [
          "You got third level, well done! {PLAYER}"
        ]
      }
    ]
  }
}
```

---

## Placeholders

Supports PlaceholderAPI placeholders such as:

- `%leveling_blocks_broken_level%`
- `%leveling_blocks_broken_experience%`
- `%leveling_blocks_broken_required_experience%`
- `%leveling_blocks_placed_level%`
- `%leveling_blocks_placed_experience%`
- `%leveling_blocks_placed_required_experience%`
- `%leveling_blocks_walked_level%`
- `%leveling_blocks_walked_experience%`
- `%leveling_blocks_walked_required_experience%`
- `%leveling_fished_amount_level%`
- `%leveling_fished_amount_experience%`
- `%leveling_fished_amount_required_experience%`
- `%leveling_mobs_killed_level%`
- `%leveling_mobs_killed_experience%`
- `%leveling_mobs_killed_required_experience%`
- `%leveling_players_killed_level%`
- `%leveling_players_killed_experience%`
- `%leveling_players_killed_required_experience%`
- `%leveling_player_deaths_level%`
- `%leveling_player_deaths_experience%`
- `%leveling_player_deaths_required_experience%`
- `%leveling_playtime_level%`
- `%leveling_playtime_experience%`
- `%leveling_playtime_required_experience%`
- `%leveling_top_1_name%`
- `%leveling_top_1_blocks_broken_level%`
- `%leveling_top_1_blocks_broken_experience%`
- `%leveling_top_1_blocks_broken_required_experience%`
- `%leveling_top_1_blocks_placed_level%`
- `%leveling_top_1_blocks_placed_experience%`
- `%leveling_top_1_blocks_placed_required_experience%`
- `%leveling_top_1_blocks_walked_level%`
- `%leveling_top_1_blocks_walked_experience%`
- `%leveling_top_1_blocks_walked_required_experience%`
- `%leveling_top_1_fished_amount_level%`
- `%leveling_top_1_fished_amount_experience%`
- `%leveling_top_1_fished_amount_required_experience%`
- `%leveling_top_1_mobs_killed_level%`
- `%leveling_top_1_mobs_killed_experience%`
- `%leveling_top_1_mobs_killed_required_experience%`
- `%leveling_top_1_players_killed_level%`
- `%leveling_top_1_players_killed_experience%`
- `%leveling_top_1_players_killed_required_experience%`
- `%leveling_top_1_player_deaths_level%`
- `%leveling_top_1_player_deaths_experience%`
- `%leveling_top_1_player_deaths_required_experience%`
- `%leveling_top_1_playtime_level%`
- `%leveling_top_1_playtime_experience%`
- `%leveling_top_1_playtime_required_experience%`

---

## API Usage

```java
// Get or create a player
LevelingPlayer player = IgniteLevelingAPI.getInstance().getOrCreatePlayer(uuid);

// Read and modify statistics
int level = IgniteLevelingAPI.getInstance().getStatisticLevel(player, StatisticType.BLOCKS_BROKEN);
IgniteLevelingAPI.getInstance().setStatisticLevel(player, StatisticType.BLOCKS_BROKEN, 5);

// Booster management
IgniteLevelingAPI.getInstance().grantBooster(player, plugin, 30, 2.0);
IgniteLevelingAPI.getInstance().revokeBooster(player);
```

### Available API Methods

- `getOrCreatePlayer(UUID uuid)`
- `getPlayer(UUID uuid)`
- `savePlayer(LevelingPlayer player)`
- `getStatistic(LevelingPlayer player, StatisticType type)`
- `getStatisticLevel(LevelingPlayer player, StatisticType type)`
- `setStatisticLevel(LevelingPlayer player, StatisticType type, int level)`
- `getStatisticExperience(LevelingPlayer player, StatisticType type)`
- `setStatisticExperience(LevelingPlayer player, StatisticType type, long exp)`
- `getStatisticProgress(LevelingPlayer player, StatisticType type)`
- `setStatisticProgress(LevelingPlayer player, StatisticType type, int progress)`
- `incrementStatisticExperience(LevelingPlayer player, StatisticType type, long exp, IgniteLeveling plugin)`
- `incrementStatisticProgress(LevelingPlayer player, StatisticType type, int progress, IgniteLeveling plugin)`
- `grantBooster(LevelingPlayer player, IgniteLeveling plugin, int duration, double multiplier)`
- `revokeBooster(LevelingPlayer player)`
- `getBoosterDuration(LevelingPlayer player)`
- `getBoosterMultiplier(LevelingPlayer player)`

## Events

IgniteLeveling exposes several events you can listen to in your plugin:

- `PlayerLevelUpEvent` — fired when a player levels up in any statistic
- `PlayerGainProgressEvent` — fired when a player gains progress in a statistic
- `PlayerGainExperienceEvent` — fired when a player gains experience in a statistic

---

## TODO

- add more statistics and system to make it disableable
- add selection between gui and text for statistics/leaderboards/player profile
- add API