package me.adixe.commonutilslib.command.arg;

import org.bukkit.command.CommandSender;

import java.util.List;

public class StringArg extends CommandArg {
    public StringArg(String identifier) {
        super(identifier);
    }

    @Override
    public Object buildValue(CommandSender sender, String input) {
        return input;
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return List.of();
    }
}
