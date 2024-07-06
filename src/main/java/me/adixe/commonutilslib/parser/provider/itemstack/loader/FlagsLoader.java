package me.adixe.commonutilslib.parser.provider.itemstack.loader;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.ConfigurationSection;

public class FlagsLoader extends ItemLoader<ItemMeta> {
    public FlagsLoader() {
        super("flags");
    }

    @Override
    protected void process(ItemMeta itemMeta, ConfigurationSection settings) {
        itemMeta.addItemFlags(settings.getStringList(path).stream()
                .map(ItemFlag::valueOf)
                .toArray(ItemFlag[]::new));
    }
}
