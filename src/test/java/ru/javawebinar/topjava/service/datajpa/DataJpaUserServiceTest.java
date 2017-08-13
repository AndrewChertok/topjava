package ru.javawebinar.topjava.service.datajpa;



import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {


    @Test
    public void testGetWithMeals() throws Exception {
        User user = service.get(USER_ID);
        List<Meal> meals = user.getMeals();
        UserTestData.MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MEALS, meals);
    }

    @Test
    public void testGetWithEmptyMealsList() throws Exception{
        User user = service.get(USER_ID);
       user.setId(null);
       user.setEmail("topjava@github.com");
       user.setMeals(null);
      User userCreated = service.create(user);
       List<Meal> meals = userCreated.getMeals();
        thrown.expect(NullPointerException.class);
       meals.size();

    }
}
