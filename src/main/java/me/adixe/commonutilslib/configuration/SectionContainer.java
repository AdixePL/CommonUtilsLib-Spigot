package me.adixe.commonutilslib.configuration;

import org.simpleyaml.configuration.ConfigurationSection;

public class SectionContainer {
    private final Configuration configuration;
    private final String file,
            path;

    public SectionContainer(Configuration configuration, String file, String path) {
        this.configuration = configuration;
        this.file = file;
        this.path = path;
    }

    public ConfigurationSection get() {
        return configuration.get(file).getConfigurationSection(path);
    }
}
