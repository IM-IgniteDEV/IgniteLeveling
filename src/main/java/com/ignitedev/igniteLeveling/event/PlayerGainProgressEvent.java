package com.ignitedev.igniteLeveling.event;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class PlayerGainProgressEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  private final LevelingPlayer levelingPlayer;
  private final int oldProgress;
  private final int newProgress;
  private final boolean isExperienceUp;

  /**
   * This is the new experience value after the progress has been updated. If experience is not
   * gained, value will be the same as old experience value.
   */
  private final long newExperience;

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
