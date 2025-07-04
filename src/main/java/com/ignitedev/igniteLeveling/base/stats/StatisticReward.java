package com.ignitedev.igniteLeveling.base.stats;

import com.ignitedev.aparecium.util.MessageUtility;
import com.ignitedev.aparecium.util.collection.RandomSelector;
import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import java.util.List;
import java.util.Random;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
public class StatisticReward {

  private final Random random = new Random();

  private final List<ItemStack> items;
  private final List<String> commands;
  private final List<String> randomCommands;
  private final List<String> message;

  public void grantReward(LevelingPlayer levelingPlayer) {
    Player player = levelingPlayer.getPlayer();

    if (items != null) {
      items.forEach(itemStack -> player.getInventory().addItem(itemStack));
    }
    if (commands != null) {
      commands.forEach(
          command ->
              Bukkit.dispatchCommand(
                  Bukkit.getConsoleSender(), command.replace("{PLAYER}", player.getName())));
    }
    if (randomCommands != null) {
      Bukkit.dispatchCommand(
          Bukkit.getConsoleSender(),
          RandomSelector.uniform(randomCommands)
              .next(random)
              .replace("{PLAYER}", player.getName()));
    }
    if (message != null) {
      message.forEach(
          message -> MessageUtility.send(player, message.replace("{PLAYER}", player.getName())));
    }
  }
}
