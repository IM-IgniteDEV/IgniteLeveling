package com.ignitedev.igniteLeveling.listener;

import static com.ignitedev.igniteLeveling.base.stats.StatisticType.BLOCKS_WALKED;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.base.stats.Statistic;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@RequiredArgsConstructor
public class PlayerMoveListener implements Listener {

  @Autowired
  private static LevelingConfiguration configuration;

  private final LevelingPlayerRepository repository;

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    LevelingPlayer byUUID = repository.findOrCreateByUUID(player.getUniqueId());

    Statistic statistic = byUUID.getStatisticMap().get(BLOCKS_WALKED);
    Location from = event.getFrom();
    Location to = event.getTo();

    if (to == null) {
      return;
    }
    if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) {
      return;
    }

    if (from.getBlockY() != to.getBlockY()) {
      return;
    }
    if (player.isFlying()) {
      return;
    }
    statistic.incrementProgress(byUUID, 1);
  }
}
