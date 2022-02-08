package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.dao.MealToDao;
import ru.javawebinar.topjava.dao.MealToDaoImpl;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealDao mealDAO = new MealDaoImpl();
    private final MealToDao mealToDao = new MealToDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("action = null, get List<MealTo>");
            request.setAttribute("mealTo", mealToDao.getList(mealDAO.getConcurrentMap()));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case ("create"):
                log.debug("to create");
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
            case ("update"):
                log.debug("get MealTo to update");
                Integer id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("meal", mealDAO.find(id));
                request.getRequestDispatcher("/updateMeal.jsp").forward(request, response);
                break;
            case ("delete"):
                Integer id2 = Integer.parseInt(request.getParameter("id"));
                log.debug("send meal id=" + id2 + " in method MealDAO.deleteMealTo");
                mealDAO.delete(id2);
                request.setAttribute("mealTo", mealToDao.getList(mealDAO.getConcurrentMap()));
                response.sendRedirect(request.getContextPath() + "/meals");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String idString = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        if (idString == null || idString.isEmpty()) {
            mealDAO.create(dateTime, description, calories);
            log.debug("create new Meal");
            request.setAttribute("mealTo", mealToDao.getList(mealDAO.getConcurrentMap()));
            response.sendRedirect(request.getContextPath() + "/meals");
        } else {
            Integer id = Integer.parseInt(idString);
            Meal meal = new Meal(id, dateTime, description, calories);
            mealDAO.update(meal);
            log.debug("update " + id + " Meal");
            request.setAttribute("mealTo", mealToDao.getList(mealDAO.getConcurrentMap()));
            response.sendRedirect(request.getContextPath() + "/meals");
        }


    }
}





