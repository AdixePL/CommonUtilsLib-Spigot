package me.adixe.commonutilslib.parser.provider.itemstack.loader;

import me.adixe.commonutilslib.configuration.Configuration;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.simpleyaml.configuration.ConfigurationSection;

public class PotionLoader extends ItemLoader<ItemMeta> {
    public PotionLoader() {
        super("potion");
    }

    @Override
    protected void process(ItemMeta itemMeta, ConfigurationSection settings) {
        if (!(itemMeta instanceof PotionMeta potionMeta)) {
            return;
        }

        for (ConfigurationSection effectSettings : Configuration.getSections(settings, path + ".effects")) {
            PotionEffectType effect = PotionEffectType.getByName(effectSettings.getString("effect"));
            int duration = (int) (20L * effectSettings.getDouble("duration"));
            int amplifier = effectSettings.getInt("amplifier");
            boolean ambient = effectSettings.getBoolean("ambient", false);
            boolean particles = effectSettings.getBoolean("particles", true);
            boolean icon = effectSettings.getBoolean("icon", true);

            potionMeta.addCustomEffect(new PotionEffect(effect, duration,
                    amplifier, ambient, particles, icon), true);
        }

        String colorPath = path + ".color";

        if (settings.contains(colorPath)) {
            String[] rgb = settings.getString(colorPath).split(":");

            potionMeta.setColor(Color.fromRGB(
                    Integer.parseInt(rgb[0]),
                    Integer.parseInt(rgb[1]),
                    Integer.parseInt(rgb[2])));
        }
    }
}
