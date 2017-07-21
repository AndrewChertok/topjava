package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealsInMemoryDaoImp;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class MealServlet extends HttpServlet{

    private MealsInMemoryDaoImp dao;

    private static String MEALS = "meals.jsp";
    private static String ADD_OR_EDIT = "mealdata.jsp";
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        dao = new MealsInMemoryDaoImp();

        dao.addMeal( new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        dao.addMeal( new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        dao.addMeal( new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        dao.addMeal( new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        dao.addMeal( new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        dao.addMeal( new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        dao.addMeal( new Meal(LocalDateTime.of(2015, Month.JUNE, 01, 10, 0), "Завтрак", 500));
        dao.addMeal( new Meal(LocalDateTime.of(2015, Month.JUNE, 01, 13, 0), "Обед", 1000));
        dao.addMeal( new Meal(LocalDateTime.of(2015, Month.JUNE, 01, 20, 0), "Ужин", 500));
    }
    



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward="";
        String action = request.getParameter("action");

        switch (action){
            case "delete":
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                dao.removeMeal(mealId);
                LOG.debug("action delete");
                response.sendRedirect("index.jsp");

            break;

            case "edit":
                forward = ADD_OR_EDIT;
                 mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = dao.getMealById(mealId);
                LOG.debug("action edit");
                request.setAttribute("meal", meal);

            break;

            case "mealsList":
                forward = MEALS;
                LOG.debug("action meals list");
                List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(dao.listMeals(), LocalTime.of(0, 1), LocalTime.of(23,59), 2000);
                request.setAttribute("meals", list);

            break;

            default:
                LOG.debug("action add meal");
                forward = ADD_OR_EDIT;

            break;
        }

        LOG.debug("forward to selected action");
        if(!action.equalsIgnoreCase("delete")) {
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.debug("entered doPost");

        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String description = request.getParameter("description");
        Integer calories = Integer.parseInt(request.getParameter("calories"));

        LOG.debug("creating a new Meal");

       Meal meal = new Meal(dateTime, description, calories);

        String mealId = request.getParameter("mealId");
        if(mealId == null || mealId.isEmpty())
            dao.addMeal(meal);
        else {
            meal.setId(Integer.parseInt(mealId));
            dao.updateMeal(meal, meal.getId());
        }

        LOG.debug("forward to meals list (doPost)");
        RequestDispatcher view = request.getRequestDispatcher(MEALS);
        List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(dao.listMeals(), LocalTime.of(0, 1), LocalTime.of(23,59), 2000);
        request.setAttribute("meals", list);
        view.forward(request, response);

    }
}
