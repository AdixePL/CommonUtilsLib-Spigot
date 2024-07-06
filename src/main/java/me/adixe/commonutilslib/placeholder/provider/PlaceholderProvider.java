package me.adixe.commonutilslib.placeholder.provider;

import java.util.HashMap;
import java.util.Map;

public abstract class PlaceholderProvider<T> {
    private final Class<T> type;
    private final String defaultPrefix;

    public PlaceholderProvider(Class<T> type, String defaultPrefix) {
        this.type = type;
        this.defaultPrefix = defaultPrefix;
    }

    public abstract Map<String, String> get(T object);

    public Map<String, String> getUnique(T object, String prefix) {
        Map<String, String> placeholders = new HashMap<>();

        for (Map.Entry<String, String> placeholder : get(object).entrySet()) {
            placeholders.put(prefix + "_" + placeholder.getKey(), placeholder.getValue());
        }

        return placeholders;
    }

    public Map<String, String> getUnique(T object) {
        return getUnique(object, defaultPrefix);
    }

    public Class<T> getType() {
        return type;
    }
}
