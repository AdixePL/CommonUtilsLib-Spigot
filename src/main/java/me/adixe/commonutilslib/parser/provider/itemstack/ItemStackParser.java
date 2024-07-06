package me.adixe.commonutilslib.parser.provider.itemstack;

import me.adixe.commonutilslib.parser.provider.Parser;
import me.adixe.commonutilslib.parser.provider.itemstack.loader.ItemLoader;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ItemStackParser extends Parser<ItemStack> {
    private final List<ItemLoader<ItemStack>> itemLoaders;
    private final List<ItemLoader<ItemMeta>> metaLoaders;

    public ItemStackParser() {
        super(ItemStack.class);

        this.itemLoaders = new ArrayList<>();
        this.metaLoaders = new ArrayList<>();
    }

    @Override
    public ItemStack get(ConfigurationSection settings) {
        String type = settings.getString("type");

        if (type == null) {
            throw new NullPointerException("No type specified for " + settings.getCurrentPath());
        }

        ItemStack itemStack = new ItemStack(
                Material.valueOf(type),
                settings.getInt("amount", 1));

        for (ItemLoader<ItemStack> loader : itemLoaders) {
            loader.load(itemStack, settings);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        for (ItemLoader<ItemMeta> loader : metaLoaders) {
            loader.load(itemMeta, settings);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public void registerItemLoader(ItemLoader<ItemStack> loader) {
        itemLoaders.add(loader);
    }

    public void registerMetaLoader(ItemLoader<ItemMeta> loader) {
        metaLoaders.add(loader);
    }
}
