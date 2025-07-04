package com.ignitedev.igniteLeveling.event;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public class PlayerLevelUpEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  private final LevelingPlayer levelingPlayer;
  private final int oldLevel;
  private final int newLevel;

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
