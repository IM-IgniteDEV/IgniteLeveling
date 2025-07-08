package com.ignitedev.igniteLeveling.command;

import com.ignitedev.aparecium.acf.BaseCommand;
import com.ignitedev.aparecium.acf.annotation.*;
import com.ignitedev.aparecium.util.DataUtility;
import com.ignitedev.aparecium.util.MessageUtility;
import com.ignitedev.aparecium.util.text.TextUtility;
import com.ignitedev.igniteLeveling.IgniteLeveling;
import com.ignitedev.igniteLeveling.base.player.LevelingPlayer;
import com.ignitedev.igniteLeveling.config.LevelingConfiguration;
import com.ignitedev.igniteLeveling.repository.LevelingPlayerRepository;
import com.twodevsstudio.simplejsonconfig.interfaces.Autowired;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("booster|boosters")
@CommandPermission("igniteleveling.command.booster")
@RequiredArgsConstructor
public class BoosterCommand extends BaseCommand {

  @Autowired
  private static LevelingConfiguration configuration;

  private final LevelingPlayerRepository repository;
  private final IgniteLeveling plugin;

  @HelpCommand
  @Default
  @CommandPermission("igniteleveling.command.booster.help")
  public void onHelp(Player player) {
    LevelingPlayer byUUID = repository.findOrCreateByUUID(player.getUniqueId());

    if (byUUID.getBoosterDuration() == 0) {
      MessageUtility.send(
          player,
          TextUtility.colorize(configuration.getPrefix() + configuration.getNoActiveBooster()));
      return;
    }
    MessageUtility.send(
        player,
        configuration.getPrefix()
            + configuration
            .getBoosterInfo()
            .replace("{MULTIPLIER}", String.valueOf(byUUID.getBoosterMultiplier()))
            .replace(
                "{TIME_LEFT}",
                DataUtility.convertMinutesToTimeString(byUUID.getBoosterDuration())));
  }

  @Subcommand("activate|start")
  @CommandPermission("igniteleveling.command.booster.admin")
  @Syntax("<player> <duration> <multiplier>")
  public void onActivate(
      CommandSender commandSender,
      @Flags("player") Player targetPlayer,
      int boosterDuration,
      double boosterMultiplier) {
    if (targetPlayer == null) {
      commandSender.sendMessage(
          TextUtility.colorize(configuration.getPrefix() + configuration.getPlayerNotFound()));
      return;
    }
    repository
        .findOrCreateByUUID(targetPlayer.getUniqueId())
        .grantBooster(plugin, boosterDuration, boosterMultiplier);
  }

  @Subcommand("revoke|stop")
  @CommandPermission("igniteleveling.command.booster.admin")
  @Syntax("<player>")
  public void onRevoke(CommandSender commandSender, @Flags("player") Player targetPlayer) {
    if (targetPlayer == null) {
      commandSender.sendMessage(
          TextUtility.colorize(configuration.getPrefix() + configuration.getPlayerNotFound()));
      return;
    }
    repository.findOrCreateByUUID(targetPlayer.getUniqueId()).revokeBooster();
    targetPlayer.sendMessage(
        TextUtility.colorize(configuration.getPrefix() + configuration.getBoosterFinished()));
    commandSender.sendMessage(
        TextUtility.colorize(configuration.getPrefix() + configuration.getConfirmationMessage()));
  }
}
