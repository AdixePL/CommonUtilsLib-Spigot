package me.adixe.commonutilslib.command;

import me.adixe.commonutilslib.configuration.SectionContainer;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandService extends BaseCommandExecutor {
    private final Map<BaseCommandExecutor, List<String>> subcommands;

    public CommandService(String name, String permission, SectionContainer settingsContainer) {
        super(name, permission, settingsContainer);

        this.subcommands = new HashMap<>();
    }

    public void register(BaseCommandExecutor executor, String... triggers) {
        subcommands.put(executor, List.of(triggers));
    }

    @Override
    protected void perform(CommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            throw new CommandException("missing-subcommand");
        }

        String trigger = args[0];

        for (Map.Entry<BaseCommandExecutor, List<String>> entry : subcommands.entrySet()) {
            BaseCommandExecutor subcommand = entry.getKey();

            if (subcommand.hasPermission(sender) && entry.getValue().contains(trigger)) {
                subcommand.execute(sender, args.length > 1 ?
                        Arrays.copyOfRange(args, 1, args.length) : new String[0]);

                return;
            }
        }

        throw new CommandException("unknown-subcommand", Map.of("trigger", trigger));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> entries = new ArrayList<>();

        if (args.length <= 1) {
            for (Map.Entry<BaseCommandExecutor, List<String>> entry : subcommands.entrySet()) {
                if (entry.getKey().hasPermission(sender)) {
                    entries.addAll(entry.getValue());
                }
            }
        } else {
            String trigger = args[0];

            for (Map.Entry<BaseCommandExecutor, List<String>> entry : subcommands.entrySet()) {
                BaseCommandExecutor subcommand = entry.getKey();

                if (subcommand.hasPermission(sender) && entry.getValue().contains(trigger)) {
                    entries.addAll(subcommand.tabComplete(sender, Arrays.copyOfRange(args, 1, args.length)));

                    break;
                }
            }
        }

        if (args.length > 0) {
            entries.removeIf(entry -> !entry.startsWith(args[args.length - 1]));
        }

        return entries;
    }
}
