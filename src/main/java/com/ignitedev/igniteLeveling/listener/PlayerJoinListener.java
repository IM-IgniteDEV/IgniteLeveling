package com.ignitedev.igniteLeveling.listener;

import com.ignitedev.igniteLeveling.IgniteLeveling;
import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import com.ignitedev.igniteLeveling.task.BoosterTimeTask;
import com.ignitedev.igniteLeveling.task.TrackPlayerTimeTask;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

  private final IgniteLeveling igniteLeveling;
  private final LevelingPlayerRepository repository;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    LevelingPlayer byUUID = repository.findOrCreateByUUID(player.getUniqueId());

    if (byUUID.getBoosterDuration() == 0) {
      return;
    }
    // we are running a booster ticking task when a player joins, since it is not counting time when
    // player is offline
    new BoosterTimeTask(byUUID).runTaskTimer(igniteLeveling, 0L, 20L * 60);
    new TrackPlayerTimeTask(byUUID).runTaskTimer(igniteLeveling, 0L, 20L * 60);
  }
}
