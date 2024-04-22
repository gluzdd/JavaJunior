package org.example.seminar2.rand;

import java.lang.reflect.Field;
import java.time.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateProcessor {
    public static final long DAY = 86400000L;
    public static final long SEC = 1000L;

    public static void ProcessRandomDate(Object obj) {
        for (Field declaredField : obj.getClass().getDeclaredFields()) {
            RandomDate annotation = declaredField.getAnnotation(RandomDate.class);
            if (annotation != null) {
                long min = annotation.min();
                long max = annotation.max();

                declaredField.setAccessible(true);

                try {
                    if (declaredField.getType().isAssignableFrom(Date.class)) {
                        declaredField.set(obj, new Date(ThreadLocalRandom.current().nextLong(min, max)));
                    } else if (declaredField.getType().isAssignableFrom(LocalDate.class)) {
                        declaredField.set(obj, LocalDate.ofEpochDay(ThreadLocalRandom.current().nextLong(min, max)/DAY));
                    } else if (declaredField.getType().isAssignableFrom(LocalDateTime.class)) {
                        declaredField.set(obj, LocalDateTime.ofEpochSecond(ThreadLocalRandom.current()
                                .nextLong(min, max) / SEC, 0, ZoneOffset.ofHours(-3)));
                    } else if (declaredField.getType().isAssignableFrom(Instant.class)) {
                        declaredField.set(obj, Instant.ofEpochMilli(ThreadLocalRandom.current().nextLong(min, max)));
                    }
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось подставить рандомное значение: " + e);
                }
            }
        }
    }
}
