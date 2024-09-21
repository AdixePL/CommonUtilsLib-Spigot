package me.adixe.commonutilslib.parser.itemstack;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.ConfigurationSection;

public class FlagsLoader extends Loader<ItemMeta> {
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
