package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealDao implements MealDao {
    private static final Logger log = getLogger(InMemoryMealDao.class);
    private final AtomicInteger lastId = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public InMemoryMealDao() {
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
    }

    public Meal create(Meal meal) {
        meal.setId(generateId());
        meals.put(meal.getId(), meal);
        log.debug("create Meal");
        return meal;
    }

    private Integer generateId() {
        return lastId.incrementAndGet();
    }

    public List<Meal> getAll() {
        log.debug("return List<Meal>");
        return new ArrayList<>(meals.values());
    }

    public Meal update(Meal meal) {
        if (meals.containsKey(meal.getId())) {
            meals.put(meal.getId(), meal);
            log.debug("meals update");
            return meal;
        }
        return null;
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
        log.debug("meals by id=" + id + " delete");
    }

    @Override
    public Meal find(int id) {
        return meals.get(id);
    }


}

