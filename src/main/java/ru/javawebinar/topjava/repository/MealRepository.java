package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal);

    boolean delete(int id, Integer userId);

    Meal get(int id, Integer userId);

    Collection<Meal> getAll(Integer userId);

    List<Meal> getFilterList(LocalDate startDate, LocalDate endDate, boolean applyFilterDate, Integer userId);
}
