package com.ignitedev.igniteLeveling.listener;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.base.stats.StatisticType;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@RequiredArgsConstructor
public class PlayerDeathListener implements Listener {

  private final LevelingPlayerRepository repository;

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    Player player = event.getEntity();
    LevelingPlayer byUUID = repository.findOrCreateByUUID(player.getUniqueId());

    byUUID.getStatisticMap().get(StatisticType.PLAYER_DEATHS).incrementProgress(byUUID, 1);
  }
}
