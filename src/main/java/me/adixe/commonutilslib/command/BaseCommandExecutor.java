package me.adixe.commonutilslib.command;

import me.adixe.commonutilslib.configuration.SectionHolder;
import me.adixe.commonutilslib.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;

public abstract class BaseCommandExecutor implements CommandExecutor, TabCompleter {
    private final String name;
    private final String permission;
    private final SectionHolder messagesHolder;

    public BaseCommandExecutor(String name, String permission, SectionHolder messagesHolder) {
        this.name = name;
        this.permission = permission;
        this.messagesHolder = messagesHolder;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        return execute(sender, args);
    }

    public boolean execute(CommandSender sender, String[] args) {
        try {
            if (!hasPermission(sender)) {
                throw new CommandException("no-permission");
            }

            perform(sender, args);

            return true;
        } catch (CommandException exception) {
            sendMessage(sender, exception.getMessage(), exception.getPlaceholders());

            return false;
        }
    }

    protected abstract void perform(CommandSender sender, String[] args) throws CommandException;

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] input) {
        return tabComplete(sender, input);
    }

    protected abstract List<String> tabComplete(CommandSender sender, String[] args);

    protected void sendMessage(CommandSender recipient, String message,
                               Map<String, String> placeholders) {
        ConfigurationSection settings = messagesHolder.get();

        try {
            MessageUtil.sendMessage(recipient, settings, name + "." + message, placeholders);
        } catch (IllegalArgumentException exception) {
            MessageUtil.sendMessage(recipient, settings, message, placeholders);
        }
    }

    protected void sendMessage(CommandSender recipient, String message) {
        sendMessage(recipient, message, Map.of());
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission);
    }

    public void register(JavaPlugin plugin) {
        plugin.getCommand(name).setExecutor(this);
    }
}
