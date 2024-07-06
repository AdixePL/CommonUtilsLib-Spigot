package me.adixe.commonutilslib.command.arg;

import me.adixe.commonutilslib.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class IntegerArg extends CommandArg {
    public IntegerArg(String identifier) {
        super(identifier);
    }

    @Override
    public Object buildValue(CommandSender sender, String input) throws CommandException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new CommandException("invalid-integer", Map.of("integer", input));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return List.of();
    }
}
