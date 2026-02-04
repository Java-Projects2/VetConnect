package com.example.vetconnect.Utils;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class Utils {
    public void updateIfPresent(String value, Consumer<String> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }
}
