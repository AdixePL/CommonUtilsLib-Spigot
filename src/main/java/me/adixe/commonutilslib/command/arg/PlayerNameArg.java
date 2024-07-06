package me.adixe.commonutilslib.command.arg;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerNameArg extends CommandArg {
    public PlayerNameArg(String identifier) {
        super(identifier);
    }

    @Override
    public Object buildValue(CommandSender sender, String input) {
        return input;
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> !(sender instanceof Player) || ((Player) sender).canSee(player))
                .map(Player::getName)
                .toList();
    }
}
