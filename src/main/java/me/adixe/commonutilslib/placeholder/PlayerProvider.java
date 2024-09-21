package me.adixe.commonutilslib.placeholder;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerProvider extends Provider<Player> {
    public PlayerProvider(String defaultPrefix) {
        super(Player.class, defaultPrefix);
    }

    @Override
    public Map<String, String> get(Player player) {
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("name", player.getName());
        placeholders.put("display_name", MiniMessage.miniMessage().serialize(
                BukkitComponentSerializer.legacy().deserialize(player.getDisplayName())));
        placeholders.put("uuid", player.getUniqueId().toString());
        placeholders.put("ip", player.getAddress().getAddress().getHostAddress());

        return placeholders;
    }
}
