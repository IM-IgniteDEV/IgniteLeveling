# IgniteLeveling

IgniteLeveling is a plugin that introduces a comprehensive player leveling and statistics system. It tracks various player activities, rewards progress, and supports boosters and leaderboards.


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


---

## Configuration

Edit `plugins/IgniteLeveling/leveling.json` to adjust experience values, rewards, blacklists, and messages.

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
- 'PlayerGainProgressEvent` — fired when a player gains progress in a statistic'
- `PlayerGainExperienceEvent` — fired when a player gains experience in a statistic

---

## TODO

- add more statistics and system to make it disableable
- add selection between gui and text for statistics/leaderboards/player profile
- add API