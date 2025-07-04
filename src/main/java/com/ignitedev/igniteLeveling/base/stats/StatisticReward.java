package com.ignitedev.igniteLeveling.base.stats;

import com.ignitedev.aparecium.util.MessageUtility;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class StatisticReward {

  private final List<ItemStack> items;
  private final List<String> commands;
  private final List<String> message;

  public void grantReward(Player player) {
    if (items != null) {
      items.forEach(itemStack -> player.getInventory().addItem(itemStack));
    }
    if (commands != null) {
      commands.forEach(
          command ->
              Bukkit.dispatchCommand(
                  Bukkit.getConsoleSender(), command.replace("{PLAYER}", player.getName())));
    }
    if (message != null) {
      message.forEach(
          message -> MessageUtility.send(player, message.replace("{PLAYER}", player.getName())));
    }
  }
}
