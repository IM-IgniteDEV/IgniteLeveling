package com.ignitedev.igniteLeveling;

import com.ignitedev.aparecium.database.MongoDBConnection;
import com.ignitedev.aparecium.database.SimpleMongo;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.listener.*;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import com.ignitedev.igniteLeveling.task.PlayersAutoSaveTask;
import com.mongodb.client.model.IndexOptions;
import com.twodevsstudio.simplejsonconfig.SimpleJSONConfig;
import com.twodevsstudio.simplejsonconfig.api.Config;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class IgniteLeveling extends JavaPlugin {

  private LevelingPlayerRepository levelingPlayerRepository;

  @Getter private SimpleMongo simpleMongo;

  @Override
  public void onEnable() {
    SimpleJSONConfig.INSTANCE.register(this);

    LevelingConfiguration config = Config.getConfig(LevelingConfiguration.class);

    initializeDatabase(config);
    initializeTasks(config);
    registerListeners(Bukkit.getPluginManager());
  }

  private void initializeTasks(LevelingConfiguration config) {
    new PlayersAutoSaveTask(this.levelingPlayerRepository)
        .runTaskTimer(this, config.getAutoSaveInterval() * 20L, config.getAutoSaveInterval() * 20L);
  }

  private void registerListeners(PluginManager pluginManager) {
    pluginManager.registerEvents(new BlockBreakListener(this, levelingPlayerRepository), this);
    pluginManager.registerEvents(new BlockPlaceListener(this, levelingPlayerRepository), this);
    pluginManager.registerEvents(new EntityDeathListener(levelingPlayerRepository), this);
    pluginManager.registerEvents(new FishingListener(levelingPlayerRepository), this);
    pluginManager.registerEvents(new PlayerDeathListener(levelingPlayerRepository), this);
    pluginManager.registerEvents(new PlayerJoinListener(this, levelingPlayerRepository), this);
    pluginManager.registerEvents(new PlayerMoveListener(levelingPlayerRepository), this);
    pluginManager.registerEvents(new PlayerQuitListener(levelingPlayerRepository), this);
  }

  private void initializeDatabase(LevelingConfiguration config) {
    MongoDBConnection mongoDBConnection = new MongoDBConnection(config.getDatabaseURL());

    this.simpleMongo =
        new SimpleMongo(mongoDBConnection, mongoDBConnection.getDatabase(config.getDatabaseName()));
    this.levelingPlayerRepository = new LevelingPlayerRepository(this.simpleMongo);
    this.simpleMongo
        .getDatabase()
        .getCollection("players")
        .createIndex(new Document("_uuid", 1), new IndexOptions().unique(true));
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
