package me.adixe.commonutilslib.command.arg;

import me.adixe.commonutilslib.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class CommandArg {
    private final String identifier;

    public CommandArg(String identifier) {
        this.identifier = identifier;
    }

    public abstract Object buildValue(CommandSender sender, String input) throws CommandException;

    public abstract List<String> tabComplete(CommandSender sender);

    public String getIdentifier() {
        return identifier;
    }
}
