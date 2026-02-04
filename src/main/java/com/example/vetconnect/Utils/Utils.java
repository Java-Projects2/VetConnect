package com.example.vetconnect.Utils;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class Utils {
    public <T> void updateIfPresent(T value, Predicate<T> condition, Consumer<T> setter) {
        if (value != null && condition.test(value)) {
            setter.accept(value);
        }
    }


    public void updateIfPresent(String value, Consumer<String> setter) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }

    public <T> void updateIfPresent(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
