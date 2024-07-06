package me.adixe.commonutilslib.parser.provider.itemstack.loader;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.ConfigurationSection;

public class LoreLoader extends ItemLoader<ItemMeta> {
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
