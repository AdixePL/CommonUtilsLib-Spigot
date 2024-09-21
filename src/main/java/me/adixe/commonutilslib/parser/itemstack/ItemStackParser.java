package me.adixe.commonutilslib.parser.itemstack;

import me.adixe.commonutilslib.parser.Parser;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ItemStackParser extends Parser<ItemStack> {
    private final List<Loader<ItemStack>> stackLoaders;
    private final List<Loader<ItemMeta>> metaLoaders;

    public ItemStackParser() {
        super(ItemStack.class);

        this.stackLoaders = new ArrayList<>();
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

        for (Loader<ItemStack> loader : stackLoaders) {
            loader.load(itemStack, settings);
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        for (Loader<ItemMeta> loader : metaLoaders) {
            loader.load(itemMeta, settings);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public void registerStackLoader(Loader<ItemStack> loader) {
        stackLoaders.add(loader);
    }

    public void registerMetaLoader(Loader<ItemMeta> loader) {
        metaLoaders.add(loader);
    }

    public void registerDefaultLoaders() {
        registerStackLoader(new EnchantmentsLoader());
        registerMetaLoader(new DisplayNameLoader());
        registerMetaLoader(new LoreLoader());
        registerMetaLoader(new UnbreakableLoader());
        registerMetaLoader(new FlagsLoader());
        registerMetaLoader(new ModifiersLoader());
        registerMetaLoader(new ArmorColorLoader());
        registerMetaLoader(new PotionLoader());
    }
}
