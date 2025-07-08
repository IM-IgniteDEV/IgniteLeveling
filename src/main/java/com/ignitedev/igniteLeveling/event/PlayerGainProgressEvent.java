package com.ignitedev.igniteLeveling.event;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event triggered when a player gains progress. This event contains information about the player's
 * progress change and experience status.
 */
@RequiredArgsConstructor
@Getter
public class PlayerGainProgressEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  /**
   * The player who gained progress.
   */
  private final LevelingPlayer levelingPlayer;

  /**
   * The player's progress before the update.
   */
  private final int oldProgress;

  /**
   * The player's progress after the update.
   */
  private final int newProgress;

  /**
   * Indicates whether the player gained experience as a result of the progress update.
   */
  private final boolean isExperienceUp;

  /**
   * The player's new experience value after the progress update. If experience is not gained, this
   * value will be the same as the old experience value.
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
