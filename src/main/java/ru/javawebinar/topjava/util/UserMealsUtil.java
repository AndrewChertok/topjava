package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> mealsWithExceed = new ArrayList<>();
        Map<LocalDate, Integer> daySumCalories = new TreeMap<>();

        mealList.forEach(meal-> daySumCalories.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (v1, v2)->v1 + v2));

        mealList.forEach(meal -> {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                mealsWithExceed.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), daySumCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay ? true : false));
        });

        return mealsWithExceed;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededUsingStreams(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> map = mealList.stream().collect(Collectors.groupingBy(x->x.getDateTime().toLocalDate(), Collectors.summingInt(x->x.getCalories())));

        List<UserMealWithExceed> mealsWithExceed = mealList.stream().filter(x->TimeUtil.isBetween(x.getDateTime().toLocalTime(), startTime, endTime)).map(x-> new UserMealWithExceed(x.getDateTime(), x.getDescription(), x.getCalories(), map.get(x.getDateTime().toLocalDate()) > caloriesPerDay ? true : false)).collect(Collectors.toList());

        return mealsWithExceed;
    }

}
