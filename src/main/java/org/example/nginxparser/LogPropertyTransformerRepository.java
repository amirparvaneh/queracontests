package org.example.nginxparser;

import org.example.nginxparser.exceptions.*;

import java.util.*;
import java.util.function.Function;

public class LogPropertyTransformerRepository {
    private static final Map<String, Function<String, Long>> transformers = new HashMap<>();

    public static void addTransformer(String propertyName,
                                      Function<String, Long> transformer) {
        transformers.put(propertyName, transformer);
    }

    public static Function<String, Long> getTransformer(String propertyName) {
        if (!transformers.containsKey(propertyName)) {
            throw new TransformerNotFoundException();
        }
        return transformers.get(propertyName);
    }
}
