package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MealRestController {


    @Autowired
    @Qualifier("mealServiceImpl")
    private MealService service;

    protected final Logger log = LoggerFactory.getLogger(getClass());


    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Meal create(Meal Meal) {
        log.info("create {}", Meal);
        return service.save(Meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal);
        service.update(meal);
    }


    public List<MealWithExceed> getAll(int caloriesPerDay) {
        log.info("getAll");
        return service.getAll(caloriesPerDay).stream().collect(Collectors.toList());
    }

    public Collection<MealWithExceed> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int caloriesPerDay) {
        log.info("getBetweenDateTime");
        return service.getBetweenDateTimes(startDateTime, endDateTime,  caloriesPerDay);
    }


    public Collection<MealWithExceed> getBetweenDates(LocalDate startDate, LocalDate endDate, int caloriesPerDay) {
        log.info("getBetweenDates");
        return service.getBetweenDates(startDate, endDate, caloriesPerDay);
    }

}