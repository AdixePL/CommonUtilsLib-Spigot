package me.adixe.commonutilslib.parser.itemstack;

import me.adixe.commonutilslib.configuration.Configuration;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.simpleyaml.configuration.ConfigurationSection;

public class EnchantmentsLoader extends Loader<ItemStack> {
    public EnchantmentsLoader() {
        super("enchantments");
    }

    @Override
    protected void process(ItemStack itemStack, ConfigurationSection settings) {
        for (ConfigurationSection enchantmentSettings : Configuration.getSections(settings, path)) {
            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(
                    enchantmentSettings.getString("enchantment")));
            int level = enchantmentSettings.getInt("level");

            itemStack.addUnsafeEnchantment(enchantment, level);
        }
    }
}
