package me.adixe.commonutilslib.configuration;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.ConfigurationSection;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Configuration {
    private final JavaPlugin plugin;
    private final Map<String, String> registeredFiles;
    private final Map<String, String[]> registeredDirectories;
    private final Map<String, Integer> registeredDataFiles;
    private final Map<String, YamlFile> configurationFiles;
    private final List<ConfigurationListener> listeners;

    public Configuration(JavaPlugin plugin) {
        this.plugin = plugin;
        this.registeredFiles = new HashMap<>();
        this.registeredDirectories = new HashMap<>();
        this.registeredDataFiles = new HashMap<>();
        this.configurationFiles = new HashMap<>();
        this.listeners = new ArrayList<>();
    }

    public void registerFile(String path, String template) {
        registeredFiles.put(path, template);
    }

    public void registerDirectory(String path, String... templates) {
        registeredDirectories.put(path, templates);
    }

    public void registerDataFile(String path, int autoSave) {
        registeredDataFiles.put(path, autoSave);
    }

    public void reload() throws IOException {
        List<String> deprecatedFiles = new ArrayList<>(configurationFiles.keySet());

        deprecatedFiles.removeAll(reloadFiles());
        deprecatedFiles.removeAll(reloadDirectories());
        deprecatedFiles.removeAll(reloadDataFiles());

        deprecatedFiles.forEach(configurationFiles::remove);

        listeners.forEach(ConfigurationListener::reload);
    }

    private List<String> reloadFiles() throws IOException {
        List<String> files = new ArrayList<>();

        for (Map.Entry<String, String> entry : registeredFiles.entrySet()) {
            File file = new File(plugin.getDataFolder(), entry.getKey());

            if (!file.exists()) {
                Files.createDirectories(file.getParentFile().toPath());

                Files.copy(plugin.getResource(entry.getValue()), file.toPath());
            }

            files.add(load(file));
        }

        return files;
    }

    private List<String> reloadDirectories() throws IOException {
        List<String> files = new ArrayList<>();

        for (Map.Entry<String, String[]> entry : registeredDirectories.entrySet()) {
            File directory = new File(plugin.getDataFolder(), entry.getKey());

            if (!directory.exists()) {
                Files.createDirectories(directory.toPath());

                for (String template : entry.getValue()) {
                    Files.copy(plugin.getResource(template), new File(directory, template).toPath());
                }
            }

            for (File file : directory.listFiles()) {
                files.add(load(file));
            }
        }

        return files;
    }

    private List<String> reloadDataFiles() throws IOException {
        List<String> files = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : registeredDataFiles.entrySet()) {
            File file = new File(plugin.getDataFolder(), entry.getKey());

            String name = formatName(file);

            files.add(name);

            if (configurationFiles.containsKey(name)) {
                continue;
            }

            if (!file.exists()) {
                Files.createDirectories(file.getParentFile().toPath());

                Files.createFile(file.toPath());
            }

            load(file);

            long delay = 20L * 60 * entry.getValue();

            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> save(name), delay, delay);
        }

        return files;
    }

    private String load(File file) throws IOException {
        YamlFile yamlFile = YamlFile.loadConfiguration(file, true);

        yamlFile.save();

        String name = formatName(file);

        configurationFiles.put(formatName(file), yamlFile);

        return name;
    }

    private String formatName(File file) {
        String fileName = file.getName();

        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    public void saveAll() {
        configurationFiles.keySet().forEach(this::save);
    }

    public void save(String name) {
        try {
            get(name).save();
        } catch (IOException exception) {
            plugin.getLogger().log(Level.SEVERE,
                    "An error occurred while saving " + name + ".",
                    exception);
        }
    }

    public void registerListener(ConfigurationListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(ConfigurationListener listener) {
        listeners.remove(listener);
    }

    public YamlFile get(String name) {
        return configurationFiles.get(name);
    }

    public static List<ConfigurationSection> getSections(ConfigurationSection section) {
        return section.getKeys(false).stream()
                .map(section::getConfigurationSection)
                .toList();
    }

    public static List<ConfigurationSection> getSections(ConfigurationSection section, String path) {
        return getSections(section.getConfigurationSection(path));
    }
}