package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.MEALS_SEQ;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {

    public static final int FIRST_MEAL =MEALS_SEQ;

    public static final Meal MEAL1 = new Meal(FIRST_MEAL, LocalDateTime.of(2017, Month.JULY, 30, 10, 0), "Завтрак", 300);
    public static final Meal MEAL2 = new Meal(FIRST_MEAL + 1, LocalDateTime.of(2017, Month.JULY, 30, 13, 0), "Обед", 700);
    public static final Meal MEAL3 = new Meal(FIRST_MEAL + 2, LocalDateTime.of(2017, Month.JULY, 30, 20, 0), "Ужин", 1000);
    public static final Meal MEAL4 = new Meal(FIRST_MEAL + 3, LocalDateTime.of(2017, Month.JULY, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL5 = new Meal(FIRST_MEAL + 4, LocalDateTime.of(2017, Month.JULY, 31, 13, 0), "Обед", 1000);
    public static final Meal MEAL6 = new Meal(FIRST_MEAL + 5, LocalDateTime.of(2017, Month.JULY, 31, 20, 0), "Ужин", 510);


    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>(
            (expected, actual)-> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                    && Objects.equals(expected.getDateTime(), actual.getDateTime())
                    && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                    )
    );

}
