package com.ignitedev.igniteLeveling.listener;

import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.base.stats.StatisticType;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@RequiredArgsConstructor
public class EntityDeathListener implements Listener {

  @Autowired private static LevelingConfiguration configuration;

  private final LevelingPlayerRepository repository;

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    LivingEntity victim = event.getEntity();
    Player killer = victim.getKiller();

    if (killer == null) {
      return;
    }
    LevelingPlayer levelingPlayer = repository.findOrCreateByUUID(killer.getUniqueId());

    if (victim instanceof Player) {
      levelingPlayer
          .getStatisticMap()
          .get(StatisticType.PLAYERS_KILLED)
          .incrementProgress(levelingPlayer, 1);
    } else {
      levelingPlayer
          .getStatisticMap()
          .get(StatisticType.MOBS_KILLED)
          .incrementProgress(levelingPlayer, 1);
    }
  }
}
