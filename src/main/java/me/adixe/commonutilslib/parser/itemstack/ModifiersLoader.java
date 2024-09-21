package me.adixe.commonutilslib.parser.itemstack;

import me.adixe.commonutilslib.configuration.Configuration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;
import org.simpleyaml.configuration.ConfigurationSection;

import java.util.UUID;

public class ModifiersLoader extends Loader<ItemMeta> {
    public ModifiersLoader() {
        super("modifiers");
    }

    @Override
    protected void process(ItemMeta itemMeta, ConfigurationSection settings) {
        for (ConfigurationSection modifierSettings : Configuration.getSections(settings, path)) {
            Attribute attribute = Attribute.valueOf(modifierSettings.getString("attribute"));

            String name = modifierSettings.getString("name");
            double amount = modifierSettings.getDouble("amount");
            AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(
                    modifierSettings.getString("operation"));
            EquipmentSlot slot = modifierSettings.contains("slot") ?
                    EquipmentSlot.valueOf(modifierSettings.getString("slot")) : null;

            itemMeta.addAttributeModifier(attribute, new AttributeModifier(
                    UUID.randomUUID(), name, amount, operation, slot));
        }
    }
}
