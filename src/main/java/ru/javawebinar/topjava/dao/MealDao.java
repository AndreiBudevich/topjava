package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentMap;

public interface MealDao {
    Meal create(LocalDateTime dateTime, String description, int calories);

    void delete(Integer id);

    Meal find(Integer id);

    Meal update(Meal meal);

    ConcurrentMap<Integer, Meal> getConcurrentMap();
}
