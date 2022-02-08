package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDaoImpl implements MealDao {
    private static final Logger log = getLogger(MealDaoImpl.class);
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final ConcurrentMap<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MealDaoImpl() {
        create(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        create(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        create(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        create(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        create(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        create(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
        create(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    }

    public Meal create(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(generationID(), dateTime, description, calories);
        meals.put(meal.getId(), meal);
        log.debug("create Meal");
        return meal;
    }

    public ConcurrentMap<Integer, Meal> getConcurrentMap() {
        return meals;
    }

    public Meal update(Meal meal) {
        meals.put(meal.getId(), meal);
        log.debug("meals update");
        return meal;
    }

    private Integer generationID() {
        return lastId.incrementAndGet();
    }

    @Override
    public void delete(Integer id) {
        meals.remove(id);
        log.debug("meals by id=" + id + " delete");
    }

    @Override
    public Meal find(Integer id) {
        return meals.get(id);
    }
}

