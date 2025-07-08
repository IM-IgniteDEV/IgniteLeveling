package com.ignitedev.igniteLeveling.repository;

import com.ignitedev.aparecium.database.SimpleMongo;
import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.base.stats.StatisticType;
import com.mongodb.client.MongoCollection;
import com.twodevsstudio.simplejsonconfig.def.Serializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class LevelingPlayerRepository {

  @Getter
  private final Map<UUID, LevelingPlayer> playersCache = new ConcurrentHashMap<>();

  private final SimpleMongo simpleMongo;

  @NotNull
  public LevelingPlayer findOrCreateByUUID(UUID uuid) {
    LevelingPlayer byUUID = findByUUID(uuid);

    if (byUUID == null) {
      LevelingPlayer levelingPlayer = new LevelingPlayer(uuid, this);
      cache(levelingPlayer);
      save(levelingPlayer);
      return levelingPlayer;
    } else {
      return byUUID;
    }
  }

  @Nullable
  private LevelingPlayer findByUUID(UUID uuid) {
    LevelingPlayer fromCache = playersCache.get(uuid);
    if (fromCache == null) {
      LevelingPlayer first =
          simpleMongo
              .get()
              .getObjectSync(
                  simpleMongo.getDatabase().getCollection("players"),
                  new Document("_uuid", uuid.toString()))
              .map(
                  document ->
                      Serializer.getInst()
                          .getGson()
                          .fromJson(((String) document.get("data")), LevelingPlayer.class))
              .first();
      if (first != null) {
        cache(first);
      }
      return first;
    }
    return fromCache;
  }

  public void cache(LevelingPlayer player) {
    playersCache.put(player.getUuid(), player);
  }

  public void save(LevelingPlayer player) {
    MongoCollection<Document> players = simpleMongo.getDatabase().getCollection("players");
    Document identifier = new Document("_uuid", player.getUuid().toString());

    simpleMongo
        .save()
        .saveObjectAsync(
            players,
            identifier,
            new Document("data", Serializer.getInst().getGson().toJson(player)));
  }

  public LevelingPlayer getTopPlayer(int order, StatisticType statisticType) {
    MongoCollection<Document> players = simpleMongo.getDatabase().getCollection("players");
    Document sort = new Document("data.statisticMap." + statisticType.name() + ".level", -1);

    Document topPlayerDoc = players.find().sort(sort).skip(order).limit(1).first();

    if (topPlayerDoc == null) {
      return null;
    }
    return Serializer.getInst()
        .getGson()
        .fromJson(((String) topPlayerDoc.get("data")), LevelingPlayer.class);
  }

  public int getPlayerRanking(LevelingPlayer player, StatisticType statisticType) {
    MongoCollection<Document> players = simpleMongo.getDatabase().getCollection("players");
    int playerLevel = player.getStatisticMap().get(statisticType).getLevel();

    Document filter =
        new Document(
            "data.statisticMap." + statisticType.name() + ".level",
            new Document("$gt", playerLevel));
    long betterPlayersCount = players.countDocuments(filter);

    return (int) betterPlayersCount + 1;
  }
}
