package me.adixe.commonutilslib.command.arg;

import me.adixe.commonutilslib.command.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class PlayerArg extends CommandArg {
    public PlayerArg(String identifier) {
        super(identifier);
    }

    @Override
    public Object buildValue(CommandSender sender, String input) throws CommandException {
        Player player = Bukkit.getPlayer(input);

        if (player != null && (!(sender instanceof Player playerSender) || playerSender.canSee(player))) {
            return player;
        }

        throw new CommandException("player-not-found", Map.of("player", input));
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> !(sender instanceof Player) || ((Player) sender).canSee(player))
                .map(Player::getName)
                .toList();
    }
}
