package me.adixe.commonutilslib.placeholder.provider;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ItemStackProvider extends PlaceholderProvider<ItemStack> {
    public ItemStackProvider(String defaultPrefix) {
        super(ItemStack.class, defaultPrefix);
    }

    @Override
    public Map<String, String> get(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        String type = itemStack.getType().name();

        MiniMessage miniMessage = MiniMessage.miniMessage();

        LegacyComponentSerializer serializer = BukkitComponentSerializer.legacy();

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("type", type);
        placeholders.put("amount", String.valueOf(itemStack.getAmount()));
        placeholders.put("display_name", itemMeta.hasDisplayName() ? miniMessage.serialize(
                serializer.deserialize(itemMeta.getDisplayName())) : type);
        placeholders.put("raw_display_name", itemMeta.hasDisplayName() ? miniMessage.serialize(
                serializer.deserialize(itemMeta.getDisplayName()).style(Style.empty())) : type);

        return placeholders;
    }
}
