package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


public class MealRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print());
        MATCHER.assertListEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
       TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
       .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
               MealsUtil.getFilteredWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1),
                       LocalTime.MIN, LocalTime.MAX, USER.getCaloriesPerDay()))));
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(null, LocalDateTime.now(), "CREATED", 1000);

        ResultActions actions = mockMvc.perform(post(REST_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(actions);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertListEquals(Arrays.asList(returned, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(USER_ID));

    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = MEAL1;
        updated.setDescription("UPDATED");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(updated)))
        .andExpect(status().isOk());
        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {

        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.JANUARY, 03, 07, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2017, Month.JULY, 03, 14, 00, 00);

      TestUtil.print(mockMvc.perform(get(REST_URL + "filter")
                .param("startDateTime", startDateTime.toString())
                .param("endDateTime", endDateTime.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getFilteredWithExceeded(
              mealService.getBetweenDates(startDateTime.toLocalDate(), endDateTime.toLocalDate(), USER_ID),
              startDateTime.toLocalTime(), endDateTime.toLocalTime(),USER.getCaloriesPerDay()))));

    }

    @Test
    public void testGetBetweenOptional() throws Exception {

        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.JANUARY, 03, 07, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2017, Month.JULY, 03, 14, 00, 00);

      TestUtil.print(mockMvc.perform(get(REST_URL + "optional")
                .param("startDate", startDateTime.toLocalDate().toString())
                .param("endDate", endDateTime.toLocalDate().toString())
              .param("startTime", startDateTime.toLocalTime().toString())
              .param("endTime", endDateTime.toLocalTime().toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getFilteredWithExceeded(
              mealService.getBetweenDates(startDateTime.toLocalDate(), endDateTime.toLocalDate(), USER_ID),
              startDateTime.toLocalTime(), endDateTime.toLocalTime(),USER.getCaloriesPerDay()))));
    }

}