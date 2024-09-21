package me.adixe.commonutilslib.parser.itemstack;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.ConfigurationSection;

public class LoreLoader extends Loader<ItemMeta> {
    public LoreLoader() {
        super("lore");
    }

    @Override
    protected void process(ItemMeta itemMeta, ConfigurationSection settings) {
        itemMeta.setLore(settings.getStringList(path).stream()
                .map(MiniMessage.miniMessage()::deserialize)
                .map(BukkitComponentSerializer.legacy()::serialize)
                .toList());
    }
}
