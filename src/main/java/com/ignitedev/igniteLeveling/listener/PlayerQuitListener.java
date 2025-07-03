package com.ignitedev.igniteLeveling.listener;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {

  private final LevelingPlayerRepository levelingPlayerRepository;

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    LevelingPlayer byUUID = levelingPlayerRepository.findOrCreateByUUID(player.getUniqueId());

    levelingPlayerRepository.save(byUUID);
  }
}
