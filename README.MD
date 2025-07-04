# IgniteLeveling

IgniteLeveling is a plugin that introduces a comprehensive player leveling and statistics system. It tracks various player activities, rewards progress, and supports boosters and leaderboards.

## Features

- Tracks player statistics such as blocks broken/placed, mobs killed, play time, and more
- Leveling system with configurable experience and rewards
- Booster system to temporarily increase experience gain
- Leaderboards and top player commands
- PlaceholderAPI integration for custom placeholders
- MongoDB support for persistent data storage
- Highly configurable via `leveling.json`

## Requirements

- Java 21+
- MongoDB
- PlaceholderAPI (for placeholders)

## Configuration

Edit `plugins/IgniteLeveling/leveling.json` to adjust experience values, rewards, blacklists, and messages.

## Commands

- `/booster` — View or manage boosters
- `/booster activate <player> <duration> <multiplier>` — Activate a booster for a player
- `/booster revoke <player>` — Revoke a player's booster
- `/statistics` or `/stats` — View your statistics
- `/statistics <player>` — View another player's statistics
- `/statistics top <statisticType>` — Show top players for a statistic
- `/statistics top <player>` — Show a player's top rankings

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

## TODO

- add more statistics and system to make it disableable
- add selection between gui and text for statistics/leaderboards/player profile
- add API