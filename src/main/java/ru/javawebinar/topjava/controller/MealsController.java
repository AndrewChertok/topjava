package ru.javawebinar.topjava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealsInMemoryDaoImp;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MealsController extends HttpServlet{

    private MealsInMemoryDaoImp dao;

    private static String MEALS = "meals.jsp";
    private static String ADD_OR_EDIT = "mealdata.jsp";
    private static final Logger LOG = LoggerFactory.getLogger(MealsController.class);


 public MealsController(){
     dao = new MealsInMemoryDaoImp();
 }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward="";
        String action = request.getParameter("action");

        switch (action){
            case "delete": {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                dao.removeMeal(mealId);
                forward = MEALS;
                LOG.debug("action delete");
                response.sendRedirect("index.jsp");
            }
            break;

            case "edit": {
                forward = ADD_OR_EDIT;
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = dao.getMealById(mealId);
                LOG.debug("action edit");
                request.setAttribute("meal", meal);
            }
            break;

            case "mealsList": {
                forward = MEALS;
                LOG.debug("action meals list");
                request.setAttribute("meals", dao.listMeals());
            }
            break;

            default:  {
                LOG.debug("action add meal");
                forward = ADD_OR_EDIT;
            }
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
        request.setAttribute("meals", dao.listMeals());
        view.forward(request, response);

    }
}
