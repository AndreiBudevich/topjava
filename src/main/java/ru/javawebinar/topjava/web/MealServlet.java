package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealRestController mealRestController;
    private static Integer userId;

    public MealServlet() {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        this.mealRestController = appCtx.getBean(MealRestController.class);
    }

     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userId = SecurityUtil.authUserId();
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")), userId);

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRestController.create(meal, userId);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userId = SecurityUtil.authUserId();
        String action = request.getParameter("action");
        if (userId == null) {
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id, userId);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, userId) :
                        mealRestController.get(getId(request), userId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            case "filter":
                boolean applyFilterDate = false;
                boolean applyFilterTime = false;
                LocalDate startDate = DateTimeUtil.START_LOKAL_DATE_DEDEFAULT;
                LocalDate endDate = DateTimeUtil.END_LOKAL_DATE_DEDEFAULT;
                LocalTime startTime = DateTimeUtil.START_TIME;
                LocalTime endTime = DateTimeUtil.END_TIME;
                String startDateString = request.getParameter("startdate");
                String endDateString = request.getParameter("enddate");
                String startTimeString = request.getParameter("starttime");
                String endTimeString = request.getParameter("endtime");

                if (startDateString != null && !startDateString.isEmpty()) {
                    startDate = LocalDate.parse(startDateString);
                    applyFilterDate = true;
                }
                if (endDateString != null && !endDateString.isEmpty()) {
                    endDate = LocalDate.parse(endDateString);
                    applyFilterDate = true;
                }
                if (startTimeString != null && !startTimeString.isEmpty()) {
                    startTime = LocalTime.parse(startTimeString);
                    applyFilterTime = true;
                }
                if (endTimeString != null && !endTimeString.isEmpty()) {
                    endTime = LocalTime.parse(endTimeString);
                    applyFilterTime = true;
                }
                request.setAttribute("startdate", startDate);
                request.setAttribute("startdate", endDate);
                request.setAttribute("startdate", startTime);
                request.setAttribute("startdate", endTime);
                request.setAttribute("meals",
                        MealsUtil.getFilteredTos(mealRestController.getFilterList(startDate, endDate, applyFilterDate, userId),
                                MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime, applyFilterTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getTos(mealRestController.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
