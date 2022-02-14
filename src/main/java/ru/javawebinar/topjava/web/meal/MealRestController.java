package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MealRestController {

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal, Integer userId) {
        return service.create(meal, userId);
    }

    public void delete(int id, Integer userId) {
        service.delete(id, userId);
    }

    public Meal get(int id, Integer userId) {
        return service.get(id, userId);
    }

    public void update(Meal user, Integer userId) {
        service.update(user, userId);
    }

    public List<Meal> getAll(Integer userId) {
        return service.getAll(userId);
    }

    public List<Meal> getFilterList(LocalDate startDate, LocalDate endDate, boolean applyFilterDate, Integer userId) {
        return service.getFilterList(startDate, endDate, applyFilterDate, userId);
    }
}
