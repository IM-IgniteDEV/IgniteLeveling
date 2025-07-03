package com.ignitedev.igniteLeveling.base.stats;

import org.jetbrains.annotations.Nullable;

public enum StatisticType {
  BLOCKS_BROKEN(),
  BLOCKS_PLACED(),
  BLOCKS_WALKED(),
  FISHED_AMOUNT(),
  MOBS_KILLED(),
  PLAYERS_KILLED(),
  PLAYER_DEATHS(),
  PLAY_TIME();

  @Nullable
  public static StatisticType getStatisticTypeByName(String name) {
    for (StatisticType value : StatisticType.values()) {
      if (value.name().equalsIgnoreCase(name) || value.name().toUpperCase().contains(name.toUpperCase())) {
        return value;
      }
    }
    return null;
  }
}
