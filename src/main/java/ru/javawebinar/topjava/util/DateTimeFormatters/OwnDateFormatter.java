package ru.javawebinar.topjava.util.DateTimeFormatters;


import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OwnDateFormatter implements Formatter<LocalDate> {

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(DATE_FORMAT);
    }

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return text.isEmpty() ? null : LocalDate.parse(text);
    }
}
