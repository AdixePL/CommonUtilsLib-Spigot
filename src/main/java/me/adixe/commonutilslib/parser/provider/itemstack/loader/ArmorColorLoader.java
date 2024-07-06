package me.adixe.commonutilslib.parser.provider.itemstack.loader;

import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.simpleyaml.configuration.ConfigurationSection;

public class ArmorColorLoader extends ItemLoader<ItemMeta> {
    public ArmorColorLoader() {
        super("armor-color");
    }

    @Override
    protected void process(ItemMeta itemMeta, ConfigurationSection settings) {
        if (!(itemMeta instanceof LeatherArmorMeta leatherArmorMeta)) {
            return;
        }

        String[] rgb = settings.getString(path).split(":");

        leatherArmorMeta.setColor(Color.fromRGB(
                Integer.parseInt(rgb[0]),
                Integer.parseInt(rgb[1]),
                Integer.parseInt(rgb[2])));
    }
}
