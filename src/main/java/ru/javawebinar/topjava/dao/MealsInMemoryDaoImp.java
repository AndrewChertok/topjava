package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class MealsInMemoryDaoImp implements MealsInMemoryDao{

  private AtomicInteger id = new AtomicInteger(0);


 private ConcurrentHashMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    {


        meals.forEach((key, map)->map.setId(key));
    }


    @Override
    public void addMeal(Meal meal) {

            meal.setId(id.incrementAndGet());
            meals.put(meal.getId(), meal);

    }

    @Override
    public void updateMeal(Meal meal, int id) {
      meals.put(id, meal);
    }

    @Override
    public void removeMeal(int id) {
          meals.remove(id);
      }

    @Override
    public Meal getMealById(int id) {
       return meals.get(id);
    }

    @Override
    public List<Meal> listMeals() {
        return meals.values().stream().collect(Collectors.toList());
    }
}
