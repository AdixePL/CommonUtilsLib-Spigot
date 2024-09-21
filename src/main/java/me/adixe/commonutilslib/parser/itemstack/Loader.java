package me.adixe.commonutilslib.parser.itemstack;

import org.simpleyaml.configuration.ConfigurationSection;

public abstract class Loader<T> {
    protected final String path;

    public Loader(String path) {
        this.path = path;
    }

    public void load(T object, ConfigurationSection settings) {
        if (settings.contains(path)) {
            process(object, settings);
        }
    }

    protected abstract void process(T object, ConfigurationSection settings);
}
