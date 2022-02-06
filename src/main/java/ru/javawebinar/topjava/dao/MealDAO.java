package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealDAO {
    List<MealTo> getMealTo();
    void addMeal (Meal meal);
    void deleteMeal(Integer id);
    Meal findMeal (Integer id);
    void updateMeal (Integer id, LocalDateTime dateTime, String description, int calories);
}
