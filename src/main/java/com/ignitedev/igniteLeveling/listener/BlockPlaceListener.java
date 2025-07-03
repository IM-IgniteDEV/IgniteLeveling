package com.ignitedev.igniteLeveling.listener;

import static com.ignitedev.igniteLeveling.base.stats.StatisticType.BLOCKS_PLACED;

import com.ignitedev.igniteLeveling.IgniteLeveling;
import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class BlockPlaceListener implements Listener {

  @Autowired private static LevelingConfiguration configuration;

  private final IgniteLeveling igniteLeveling;
  private final LevelingPlayerRepository repository;

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    Block block = event.getBlock();

    if (configuration.getPlaceBlacklist().contains(block.getType())) {
      return;
    }
    new BukkitRunnable() {
      @Override
      public void run() {
        if (event.isCancelled()) {
          // event might be blocked by another plugin, that's we delay it by 2 ticks
          return;
        }
        LevelingPlayer levelingPlayer =
            repository.findOrCreateByUUID(event.getPlayer().getUniqueId());

        levelingPlayer.getStatisticMap().get(BLOCKS_PLACED).incrementProgress(levelingPlayer, 1);
      }
    }.runTaskLater(igniteLeveling, 2);
  }
}
