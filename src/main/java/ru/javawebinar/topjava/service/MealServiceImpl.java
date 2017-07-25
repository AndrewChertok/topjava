package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;

import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;


@Service
public class MealServiceImpl implements MealService {

    @Autowired
    @Qualifier("inMemoryMealRepository")
    private InMemoryMealRepositoryImpl repository;


    public void setRepository(InMemoryMealRepositoryImpl repository) {
    }


    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }


    @Override
    public void delete(int id, int userId) throws NotFoundException{
       checkNotFoundWithId(repository.delete(id, userId), id);
    }


    @Override
    public Meal get(int id, int userId) throws NotFoundException{
        return checkNotFoundWithId(repository.get(id, userId), id);
    }


    @Override
    public Collection<MealWithExceed> getAll(int userId) {
        return MealsUtil.getWithExceeded(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public Collection<MealWithExceed> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int caloriesperDay, int userId) {
        return MealsUtil.getFilteredWithExceededByCycle(repository.getBetweenDates(startDateTime.toLocalDate(), endDateTime.toLocalDate(), userId), startDateTime.toLocalTime(), endDateTime.toLocalTime(), caloriesperDay);
    }



}