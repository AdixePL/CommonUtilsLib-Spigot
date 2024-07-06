package me.adixe.commonutilslib.placeholder.provider;

import org.bukkit.command.CommandSender;

import java.util.Map;

public class CommandSenderProvider extends PlaceholderProvider<CommandSender> {
    public CommandSenderProvider(String defaultPrefix) {
        super(CommandSender.class, defaultPrefix);
    }

    @Override
    public Map<String, String> get(CommandSender sender) {
        return Map.of("name", sender.getName());
    }
}
