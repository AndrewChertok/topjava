package ru.javawebinar.topjava.to;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDateTime;

public class MealWithExceed {
    private final Integer id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public MealWithExceed( @JsonSetter("id") Integer id,
                           @JsonSetter("dateTime") LocalDateTime dateTime,
                           @JsonSetter("description") String description,
                           @JsonSetter("calories") int calories,
                           @JsonSetter("exceed") boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    @JsonGetter("id")
    public Integer getId() {
        return id;
    }

    @JsonGetter("dateTime")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonGetter("calories")
    public int getCalories() {
        return calories;
    }

    @JsonGetter("exceed")
    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}