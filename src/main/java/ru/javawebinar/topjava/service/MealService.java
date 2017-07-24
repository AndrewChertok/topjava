package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

public interface MealService {


    Meal save(Meal meal);

    void update(Meal meal);

    void delete(int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    Collection<MealWithExceed> getAll(int caloriesPerDay);

    Collection<MealWithExceed> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int caloriesPerDay);













}