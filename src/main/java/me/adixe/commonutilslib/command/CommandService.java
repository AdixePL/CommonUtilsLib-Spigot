package me.adixe.commonutilslib.command;

import me.adixe.commonutilslib.configuration.SectionHolder;
import org.bukkit.command.CommandSender;

import java.util.*;

public class CommandService extends BaseCommandExecutor {
    private final Map<BaseCommandExecutor, List<String>> subcommands;
    private final boolean requireSubcommand;

    public CommandService(String name, String permission, SectionHolder messagesHolder,
                          boolean requireSubcommand) {
        super(name, permission, messagesHolder);

        this.subcommands = new LinkedHashMap<>();
        this.requireSubcommand = requireSubcommand;
    }

    public CommandService(String name, String permission, SectionHolder messagesHolder) {
        this(name, permission, messagesHolder, true);
    }

    public void register(BaseCommandExecutor executor, String... triggers) {
        subcommands.put(executor, List.of(triggers));
    }

    @Override
    protected void perform(CommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            BaseCommandExecutor subcommand = subcommands.keySet().iterator().next();

            if (!requireSubcommand && subcommand.hasPermission(sender)) {
                subcommand.execute(sender, new String[0]);
            } else {
                throw new CommandException("missing-subcommand");
            }
        } else {
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
