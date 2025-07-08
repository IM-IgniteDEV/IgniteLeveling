package com.ignitedev.igniteLeveling.task;

import com.ignitedev.aparecium.util.text.TextUtility;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class PlayersAutoSaveTask extends BukkitRunnable {

  @Autowired
  private static LevelingConfiguration configuration;

  private final LevelingPlayerRepository repository;

  @Override
  public void run() {
    repository.getPlayersCache().values().forEach(repository::save);
    Bukkit.broadcastMessage(
        TextUtility.colorize(configuration.getPrefix() + configuration.getAutoSaveMessage()));
  }
}
