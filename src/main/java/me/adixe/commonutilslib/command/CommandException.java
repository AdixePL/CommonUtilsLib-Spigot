package me.adixe.commonutilslib.command;

import java.util.Collections;
import java.util.Map;

public class CommandException extends Exception {
    private final String message;
    private final Map<String, String> placeholders;

    public CommandException(String message, Map<String, String> placeholders) {
        this.message = message;
        this.placeholders = placeholders;
    }

    public CommandException(String message) {
        this(message, Collections.emptyMap());
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }
}
