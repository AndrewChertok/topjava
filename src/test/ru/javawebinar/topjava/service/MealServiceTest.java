package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception{
        dbPopulator.execute();
    }


    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(FIRST_MEAL, ADMIN_ID);
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testDelete() throws Exception {
        service.delete(1000000, USER_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        List<Meal> mealsBetweenDates = service.getBetweenDates(MEAL1.getDate(), MEAL3.getDate(), ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL1, MEAL2, MEAL3), mealsBetweenDates);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        List<Meal> mealsBetweenDateTime =
                service.getBetweenDateTimes(MEAL1.getDateTime(), MEAL5.getDateTime(), ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL1, MEAL2, MEAL3), mealsBetweenDateTime);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Meal> allMeals = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1), allMeals);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal mealForUpdating = service.get(MEAL1.getId()+4, USER_ID);
        mealForUpdating.setDescription("Ужин");
        mealForUpdating.setDateTime(LocalDateTime.of(2017, Month.JULY, 02, 20, 00));
        Meal updated = service.update(mealForUpdating, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(mealForUpdating), service.getBetweenDates(mealForUpdating.getDate(), mealForUpdating.getDate(), USER_ID) );

    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.of(2017, Month.JULY, 01, 10, 0), "Завтрак", 2000);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
       MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, newMeal), service.getAll(USER_ID));
    }

}