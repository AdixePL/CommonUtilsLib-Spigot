package me.adixe.commonutilslib.parser.provider.itemstack.loader;

import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.ConfigurationSection;

public class UnbreakableLoader extends ItemLoader<ItemMeta> {
    public UnbreakableLoader() {
        super("unbreakable");
    }

    @Override
    protected void process(ItemMeta itemMeta, ConfigurationSection settings) {
        itemMeta.setUnbreakable(settings.getBoolean(path));
    }
}
