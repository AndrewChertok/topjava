package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

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
        int userId =  AuthorizedUser.id();
        log.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.save(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        int userId =  AuthorizedUser.id();
        log.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal) {
        int userId = AuthorizedUser.id();
        log.info("update {} for User {}", meal, userId);
        service.update(meal, userId);
    }


    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        log.info("getAll for User {}", userId);
        return service.getAll(userId).stream().collect(Collectors.toList());
    }

    public Collection<MealWithExceed> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int caloriesPerDay) {
        int userId = AuthorizedUser.id();
        log.info("getBetween dates {} - {} for time {} - {} for User {}", startDateTime, endDateTime, userId);
        return service.getBetweenDateTimes(startDateTime, endDateTime,  caloriesPerDay, userId);
    }


}