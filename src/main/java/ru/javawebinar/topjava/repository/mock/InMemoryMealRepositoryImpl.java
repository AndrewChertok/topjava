package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;



@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);


    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);



    {
        MealsUtil.MEALS.forEach(meal-> repository.put(counter.incrementAndGet(), meal));
    }




    @Override
    public Meal save(Meal meal, int userId) {
        LOG.info("save {}", meal);
        if (meal.isNew())
            meal.setId(counter.incrementAndGet());

         meal.setUserId(userId);
         repository.put(meal.getId(), meal);
        return repository.containsValue(meal)? meal : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        LOG.info("delete meal by id {}", id);
        Meal isDeleted = repository.remove(id);
        return isDeleted != null;
    }

    @Override
    public Meal get(int id, int userId) {
        LOG.info("get meal by id {}", id);
        Meal meal = repository.get(id);
            return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        LOG.info("getAll meals by user ID {}", userId );
        return repository.values().stream().filter(meal-> meal.getUserId() == userId).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetweenDates(LocalDate start, LocalDate end, int userId) {

        LOG.info("get meals between dates by user ID {}", userId );
        return getAll(userId).stream().filter(meal-> DateTimeUtil.isBetweenDate(meal.getDate(), start, end)).
              collect(Collectors.toList());

    }
}

