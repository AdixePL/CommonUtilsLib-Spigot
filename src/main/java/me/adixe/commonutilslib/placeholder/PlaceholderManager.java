package me.adixe.commonutilslib.placeholder;

import me.adixe.commonutilslib.placeholder.provider.PlaceholderProvider;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderManager {
    private final List<PlaceholderProvider<?>> placeholderProviders = new ArrayList<>();

    public <T> void register(PlaceholderProvider<T> placeholderProvider) {
        placeholderProviders.add(placeholderProvider);
    }

    @SuppressWarnings("unchecked")
    public <T> PlaceholderProvider<T> get(Class<T> type) {
        return (PlaceholderProvider<T>) placeholderProviders.stream()
                .filter(placeholderProvider -> placeholderProvider.getType().equals(type))
                .findFirst()
                .orElse(null);
    }
}
