package me.adixe.commonutilslib.parser.provider.itemstack.loader;

import org.simpleyaml.configuration.ConfigurationSection;

public abstract class ItemLoader<T> {
    protected final String path;

    public ItemLoader(String path) {
        this.path = path;
    }

    public void load(T object, ConfigurationSection settings) {
        if (settings.contains(path)) {
            process(object, settings);
        }
    }

    protected abstract void process(T object, ConfigurationSection settings);
}
