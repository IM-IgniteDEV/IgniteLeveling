package com.ignitedev.igniteLeveling.event;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event triggered when a player gains experience. This event contains information about the
 * player's experience change and level status.
 */
@RequiredArgsConstructor
@Getter
public class PlayerGainExperienceEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  /** The player who gained experience. */
  private final LevelingPlayer levelingPlayer;

  /** The player's experience before the gain. */
  private final long oldExperience;

  /** The player's experience after the gain. */
  private final long newExperience;

  /** Indicates whether the player leveled up as a result of gaining experience. */
  private final boolean isLevelUp;

  /**
   * The player's new level after gaining experience. If the player did not level up, this will be
   * the same as their current level.
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
