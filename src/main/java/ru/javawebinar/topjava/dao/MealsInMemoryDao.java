package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

/**
 * Created by Andrey on 7/18/2017.
 */
public interface MealsInMemoryDao {
    public void addMeal(Meal meal) ;
    public void updateMeal(Meal meal, int id) ;
    public void removeMeal(int id) ;
    public Meal getMealById(int id) ;
    public List<Meal> listMeals() ;
}
