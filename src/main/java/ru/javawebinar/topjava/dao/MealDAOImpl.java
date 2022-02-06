package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDAOImpl implements MealDAO {
    private static final Logger log = getLogger(MealDAOImpl.class);
    private static AtomicInteger lastId = new AtomicInteger(0);
    private static List<Meal> meals = new CopyOnWriteArrayList<>();

    static {
        meals.add(new Meal(generationID(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(generationID(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(generationID(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(generationID(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(generationID(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(generationID(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        meals.add(new Meal(generationID(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
    }

    private MealDAOImpl() {
    }

    private static class MealDAOImplHolder {
        public static final MealDAOImpl HOLDER_INSTANCE = new MealDAOImpl();
    }

    public static MealDAO getInstance() {
        return MealDAOImplHolder.HOLDER_INSTANCE;
    }

    public void addMeal(Meal meal) {
        log.debug("addMeal");
        meals.add(meal);
    }

    public synchronized void updateMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        Meal meal = findMeal(id);
        if (meal.getDateTime().equals(dateTime))
            meal.setDateTime(dateTime);
        if (!meal.getDescription().equals(description)) {
            meal.setDescription(description);
        }
        if (meal.getCalories() != calories) {
            meal.setCalories(calories);
        }
        log.debug("updateMeal");
    }

    final int caloriesPerDay = 2000;
    @Override
    public List<MealTo> getMealTo() {
        log.debug("return view List<MealTo>");
        return MealsUtil.filteredByStreams(meals, caloriesPerDay);
    }

    public static Integer generationID() {
        return lastId.incrementAndGet();
    }

    @Override
    public synchronized void deleteMeal(Integer id) {
        for (Meal meal : meals) {
            if (meal.getId().equals(id)) {
                log.debug("meals by id=" + id + " found");
                meals.remove(meal);
                log.debug("meals by id=" + id + " delete");
                return;
            }
        }
    }

    @Override
    public synchronized Meal findMeal(Integer id) {
        for (Meal meal : meals) {
            if (meal.getId().equals(id)) {
                log.debug("meals by id=" + id + " found");
                return meal;
            }
        }
        return null;
    }
}
