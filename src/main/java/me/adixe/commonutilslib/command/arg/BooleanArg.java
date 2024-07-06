package me.adixe.commonutilslib.command.arg;

import me.adixe.commonutilslib.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class BooleanArg extends CommandArg {
    public BooleanArg(String identifier) {
        super(identifier);
    }

    @Override
    public Object buildValue(CommandSender sender, String input) throws CommandException {
        if (input.equals("true")) {
            return true;
        } else if (input.equals("false")) {
            return false;
        }

        throw new CommandException("invalid-boolean", Map.of("boolean", input));
    }

    @Override
    public List<String> tabComplete(CommandSender sender) {
        return List.of("true", "false");
    }
}
