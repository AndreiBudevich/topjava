package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public interface MealToDao {
    List<MealTo> getList(ConcurrentMap<Integer, Meal> meals);
}
