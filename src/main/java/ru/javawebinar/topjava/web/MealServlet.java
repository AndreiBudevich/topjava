package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealDao mealDAO;

    public MealServlet() {
        this.mealDAO = new MealDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            log.debug("action = null, get List<MealTo>");
            request.setAttribute("mealTos", MealsUtil.getSortList(mealDAO.getList()));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case ("create"):
                log.debug("to create");
                request.getRequestDispatcher("/addMeal.jsp").forward(request, response);
                break;
            case ("update"): {
                log.debug("get MealTo to update");
                Integer id = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("meal", mealDAO.find(id));
                request.getRequestDispatcher("/updateMeal.jsp").forward(request, response);
                break;
            }
            case ("delete"): {
                Integer id = Integer.parseInt(request.getParameter("id"));
                log.debug("MealDaoInMemory#delete id=" + id);
                mealDAO.delete(id);
                response.sendRedirect(request.getContextPath() + "/meals");
                break;
            }
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
            Meal meal = new Meal(dateTime, description, calories);
            log.debug("MealDaoInMemory#create");
            mealDAO.create(meal);

        } else {
            Integer id = Integer.parseInt(idString);
            Meal meal = new Meal(id, dateTime, description, calories);
            log.debug("MealDaoInMemory#update id=" + id);
            mealDAO.update(meal);
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}





