package me.adixe.commonutilslib.placeholder;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderManager {
    private final List<Provider<?>> providers = new ArrayList<>();

    public <T> void register(Provider<T> provider) {
        providers.add(provider);
    }

    @SuppressWarnings("unchecked")
    public <T> Provider<T> get(Class<T> type) {
        return (Provider<T>) providers.stream()
                .filter(provider -> provider.getType().equals(type))
                .findFirst()
                .orElse(null);
    }
}
