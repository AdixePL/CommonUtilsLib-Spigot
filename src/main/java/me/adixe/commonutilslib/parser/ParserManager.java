package me.adixe.commonutilslib.parser;

import me.adixe.commonutilslib.parser.provider.Parser;

import java.util.ArrayList;
import java.util.List;

public class ParserManager {
    private final List<Parser<?>> parsers = new ArrayList<>();

    public <T> void register(Parser<T> parser) {
        parsers.add(parser);
    }

    @SuppressWarnings("unchecked")
    public <T> Parser<T> get(Class<T> type) {
        return (Parser<T>) parsers.stream()
                .filter(parserProvider -> parserProvider.getType().equals(type))
                .findFirst()
                .orElse(null);
    }
}
