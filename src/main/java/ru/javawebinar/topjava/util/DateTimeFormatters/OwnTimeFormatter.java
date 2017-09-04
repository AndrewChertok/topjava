package ru.javawebinar.topjava.util.DateTimeFormatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class OwnTimeFormatter implements Formatter<LocalTime> {

    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.format(TIME_FORMAT);
    }

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return text.isEmpty() ? null : LocalTime.parse(text);
    }
}
