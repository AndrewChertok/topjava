package ru.javawebinar.topjava.web.meal;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/profile/meals")
public class MealAjaxController  extends AbstractMealController{

    @DeleteMapping("/{id}")
    @Override
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @PostMapping
    public void createOrUpdate(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories") int calories) {
        Meal meal = new Meal(id, dateTime, description, calories);
        if(meal.isNew()) super.create(meal);
        else super.update(meal, id);
    }

    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<MealWithExceed> getBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                           @RequestParam(value = "startTime", required = false) LocalTime startTime,
                                           @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                           @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}