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
        meals.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        meals.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.JUNE, 01, 10, 0), "Завтрак", 500));
        meals.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.JUNE, 01, 13, 0), "Обед", 1000));
        meals.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.JUNE, 01, 20, 0), "Ужин", 500));

        meals.forEach((key, map)->map.setId(key));
    }


    @Override
    public void addMeal(Meal meal) {
        synchronized (meal) {
            meal.setId(id.incrementAndGet());
            meals.put(id.get(), meal);
        }
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
    public List<MealWithExceed> listMeals() {
        return MealsUtil.getFilteredWithExceeded(meals.values().stream().collect(Collectors.toList()), LocalTime.of(0, 1), LocalTime.of(23, 59), 2000);
    }
}
