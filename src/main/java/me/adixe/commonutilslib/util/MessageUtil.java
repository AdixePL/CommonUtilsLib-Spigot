package me.adixe.commonutilslib.util;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.ConfigurationSection;

import java.time.Duration;
import java.util.Map;

public class MessageUtil {
    private static BukkitAudiences audiences;

    public static void registerAudiences(JavaPlugin plugin) {
        audiences = BukkitAudiences.create(plugin);
    }

    public static void close() {
        audiences.close();
    }

    public static void sendMessage(Audience target, ConfigurationSection settings,
                                   String path, Map<String, String> placeholders) {
        MiniMessage miniMessage = MiniMessage.miniMessage();

        if (settings.isConfigurationSection(path)) {
            String messagePath = path + ".message";

            if (settings.contains(messagePath)) {
                target.sendMessage(miniMessage.deserialize(translate(
                        settings.getString(messagePath), placeholders)));
            }

            String titlePath = path + ".title";

            if (settings.contains(titlePath)) {
                ConfigurationSection titleSettings = settings.getConfigurationSection(titlePath);

                Component title = miniMessage.deserialize(translate(
                        titleSettings.getString("title"), placeholders));

                Component subtitle = miniMessage.deserialize(translate(
                        titleSettings.getString("subtitle"), placeholders));

                Title.Times times = Title.Times.times(
                        Duration.ofMillis((long) (1000 * titleSettings.getDouble("fade-in", 0))),
                        Duration.ofMillis((long) (1000 * titleSettings.getDouble("stay", 3))),
                        Duration.ofMillis((long) (1000 * titleSettings.getDouble("fade-out", 0.5))));

                target.showTitle(Title.title(title, subtitle, times));
            }

            String actionBarPath = path + ".action-bar";

            if (settings.contains(actionBarPath)) {
                target.sendActionBar(miniMessage.deserialize(translate(
                        settings.getString(actionBarPath), placeholders)));
            }

            String soundPath = path + ".sound";

            if (settings.contains(soundPath)) {
                String[] soundSplit = settings.getString(soundPath).split(":");

                target.playSound(Sound.sound(Key.key(soundSplit[0]),
                        Sound.Source.MASTER,
                        soundSplit.length >= 2 ? Float.parseFloat(soundSplit[1]) : 1,
                        soundSplit.length >= 3 ? Float.parseFloat(soundSplit[2]) : 1));
            }
        } else if (settings.contains(path)) {
            String message = settings.getString(path);

            if (!message.isEmpty()) {
                target.sendMessage(miniMessage.deserialize(translate(message, placeholders)));
            }
        } else {
            throw new IllegalArgumentException("Invalid message format for " + path);
        }
    }

    public static void sendMessage(Audience target, ConfigurationSection settings, String path) {
        sendMessage(target, settings, path, Map.of());
    }

    public static void sendMessage(CommandSender target, ConfigurationSection settings,
                                   String path, Map<String, String> placeholders) {
        sendMessage(audiences.sender(target), settings, path, placeholders);
    }

    public static void sendMessage(CommandSender target, ConfigurationSection settings, String path) {
        sendMessage(target, settings, path, Map.of());
    }

    public static String translate(String text, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            text = text.replace("%" + entry.getKey() + "%", entry.getValue());
        }

        return text;
    }

    public static String translate(String text) {
        return translate(text, Map.of());
    }
}
