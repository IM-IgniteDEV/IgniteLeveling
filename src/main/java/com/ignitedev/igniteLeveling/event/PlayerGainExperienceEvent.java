package com.ignitedev.igniteLeveling.event;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class PlayerGainExperienceEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  private final LevelingPlayer levelingPlayer;
  private final long oldExperience;
  private final long newExperience;
  private final boolean isLevelUp;
  /**
   * New level after gaining experience. If the player did not level up, this will be the same as the current level.
   */
  private final int newLevel;

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
