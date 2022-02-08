package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import static org.slf4j.LoggerFactory.getLogger;

public class MealToDaoImpl implements MealToDao {
    private static final Logger log = getLogger(MealToDaoImpl.class);
    private final int caloriesPerDay = 2000;

    @Override
    public List<MealTo> getList(ConcurrentMap<Integer, Meal> meals) {
        log.debug("return view List<MealTo>");
        return MealsUtil.filteredByStreamsСurMap(meals, caloriesPerDay);
    }
}


