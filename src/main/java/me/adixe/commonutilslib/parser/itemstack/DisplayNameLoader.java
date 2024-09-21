package me.adixe.commonutilslib.parser.itemstack;

import me.adixe.commonutilslib.util.MessageUtil;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.ConfigurationSection;

public class DisplayNameLoader extends Loader<ItemMeta> {
    public DisplayNameLoader() {
        super("display-name");
    }

    @Override
    protected void process(ItemMeta itemMeta, ConfigurationSection settings) {
        itemMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(
                MiniMessage.miniMessage().deserialize(MessageUtil.translate(settings.getString(path)))));
    }
}
