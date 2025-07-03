package com.ignitedev.igniteLeveling.listener;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.base.stats.StatisticType;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

@RequiredArgsConstructor
public class FishingListener implements Listener {

  private final LevelingPlayerRepository repository;

  @EventHandler
  public void onFishCaught(PlayerFishEvent event) {
    PlayerFishEvent.State state = event.getState();

    if (state != PlayerFishEvent.State.CAUGHT_FISH) {
      return;
    }
    LevelingPlayer byUUID = repository.findOrCreateByUUID(event.getPlayer().getUniqueId());
    byUUID.getStatisticMap().get(StatisticType.FISHED_AMOUNT).incrementProgress(byUUID, 1);
  }
}
