package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealDAO mealDAO = MealDAOImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";

        if (request.getParameter("action") == null) {
            log.debug("action = null, get List<MealTo>");
            request.setAttribute("mealTos", mealDAO.getMealTo());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("listMeals")) {
            log.debug("get List<MealTo>");
            request.setAttribute("mealTos", mealDAO.getMealTo());
            forward = "/meals.jsp";
        }

        if (action.equalsIgnoreCase("update")) {
            log.debug("get MealTo to update");
            Integer id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("meal", mealDAO.findMeal(id));
            forward = "/updateMeal.jsp";
        }

        if (action.equalsIgnoreCase("delete")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            log.debug("send meal id=" + id + " in method MealDAO.deleteMealTo");
            mealDAO.deleteMeal(id);
            request.setAttribute("mealTos", mealDAO.getMealTo());
            forward = "/meals.jsp";
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (action.equalsIgnoreCase("createMeal")) {
            mealDAO.addMeal(new Meal(MealDAOImpl.generationID(), dateTime, description, calories));
            log.debug("create new Meal");
        }


        if (action.equalsIgnoreCase("updateMeal")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            mealDAO.updateMeal(id, dateTime, description, calories);
            log.debug("update " + id +" Meal");
        }

        request.setAttribute("mealTos", mealDAO.getMealTo());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}





