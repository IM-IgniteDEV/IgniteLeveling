package com.ignitedev.igniteLeveling.task;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class BoosterTimeTask extends BukkitRunnable {

  private final LevelingPlayer levelingPlayer;

  private Player cachedPlayer;

  @Override
  public void run() {
    if (cachedPlayer == null) {
      cachedPlayer = Bukkit.getPlayer(levelingPlayer.getUuid());
      // we are caching the player to avoid multiple lookups
    }
    if (cachedPlayer == null || !cachedPlayer.isOnline()) {
      // player is offline or not found, so we cancel the task
      this.cancel();
      return;
    }
    levelingPlayer.setBoosterDuration(levelingPlayer.getBoosterDuration() - 1);

    if (levelingPlayer.getBoosterDuration() <= 0) {
      levelingPlayer.revokeBooster();
    }
  }
}
