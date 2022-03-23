package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class RestMealController extends MealController {
    public RestMealController(MealService service) {
        super(service);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        super.update(meal, id, userId);
        service.update(meal, userId);
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime,
                                   LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        return super.getBetween(startDate, endDate, startTime, endTime, userId);
    }
}