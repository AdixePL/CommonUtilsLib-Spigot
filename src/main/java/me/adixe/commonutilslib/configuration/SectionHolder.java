package me.adixe.commonutilslib.configuration;

import org.simpleyaml.configuration.ConfigurationSection;

public record SectionHolder(Configuration configuration, String file, String path) {
    public ConfigurationSection get() {
        return configuration.get(file).getConfigurationSection(path);
    }
}
