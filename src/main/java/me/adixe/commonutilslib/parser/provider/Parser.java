package me.adixe.commonutilslib.parser.provider;

import org.simpleyaml.configuration.ConfigurationSection;

public abstract class Parser<T> {
    private final Class<T> type;

    public Parser(Class<T> type) {
        this.type = type;
    }

    public abstract T get(ConfigurationSection settings);

    public T get(ConfigurationSection settings, String path) {
        return get(settings.getConfigurationSection(path));
    }

    public Class<T> getType() {
        return type;
    }
}
