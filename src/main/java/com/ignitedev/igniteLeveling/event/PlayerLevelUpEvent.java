package com.ignitedev.igniteLeveling.event;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event triggered when a player levels up. This event contains information about the player's level
 * change.
 */
@RequiredArgsConstructor
@Getter
public class PlayerLevelUpEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  /**
   * The player who leveled up.
   */
  private final LevelingPlayer levelingPlayer;

  /**
   * The player's level before leveling up.
   */
  private final int oldLevel;

  /**
   * The player's level after leveling up.
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
