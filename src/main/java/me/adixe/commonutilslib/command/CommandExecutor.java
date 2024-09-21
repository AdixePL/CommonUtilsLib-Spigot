package me.adixe.commonutilslib.command;

import me.adixe.commonutilslib.command.arg.CommandArg;
import me.adixe.commonutilslib.configuration.SectionHolder;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandExecutor extends BaseCommandExecutor {
    private final List<CommandArg> args;
    private final int requiredArgs;

    public CommandExecutor(String name, String permission, SectionHolder messagesHolder,
                           List<CommandArg> args, int requiredArgs) {
        super(name, permission, messagesHolder);

        this.args = args;
        this.requiredArgs = requiredArgs;
    }

    @Override
    public void perform(CommandSender sender, String[] input) throws CommandException {
        Map<String, Object> argsValues = new HashMap<>();

        int argIndex = 0;

        for (CommandArg arg : args) {
            if (argIndex > input.length - 1) {
                break; // Cancel when data is missing in input for args
            }

            StringBuilder argInput = new StringBuilder();

            if (input[argIndex].startsWith("\"")) {
                while (true) {
                    if (argIndex > input.length - 1) {
                        throw new CommandException("no-arg-end", Map.of("arg", arg.getIdentifier()));
                    }

                    String entry = input[argIndex];

                    argInput.append(entry.replace("\"", ""));

                    if (entry.endsWith("\"")) {
                        break;
                    }

                    argInput.append(" ");

                    argIndex++;
                }
            } else {
                argInput.append(input[argIndex]);
            }

            argsValues.put(arg.getIdentifier(), arg.buildValue(sender, argInput.toString()));

            argIndex++;
        }

        if (argsValues.size() < requiredArgs) {
            StringBuilder usage = new StringBuilder();

            int index = 0;

            for (CommandArg arg : args) {
                String argIdentifier = arg.getIdentifier();

                if (index <= requiredArgs - 1) {
                    usage.append("<").append(argIdentifier).append(">");
                } else {
                    usage.append("[").append(argIdentifier).append("]");
                }

                if (index + 1 < args.size()) {
                    usage.append(" ");
                }

                index++;
            }

            throw new CommandException("invalid-usage", Map.of("usage", usage.toString()));
        }

        execute(sender, argsValues);
    }

    protected abstract void execute(CommandSender sender, Map<String, Object> argsValues) throws CommandException;

    @Override
    public List<String> tabComplete(CommandSender sender, String[] input) {
        if (input.length <= args.size()) {
            return args.get(input.length - 1).tabComplete(sender); // FIXME: Include quote tab-completing
        }

        return List.of();
    }
}
