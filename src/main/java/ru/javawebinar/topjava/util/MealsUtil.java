package ru.javawebinar.topjava.util;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsUtil {

    private static final Logger log = getLogger(MealsUtil.class);
    private static final int caloriesPerDay = 2000;

    public static List<MealTo> getList(List<Meal> meals) {
        log.debug("return view List<MealTo>");
        return MealsUtil.filteredByStreams(meals, caloriesPerDay);
    }

    public static Predicate<Meal> predicate;
    public static List<MealTo> filteredByStreams(List<Meal> meals, int caloriesPerDay, LocalTime... localTimes) {
        if (localTimes.length == 0) {
            predicate = meal -> true;
        } else {
            predicate = meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), localTimes[0], localTimes[1]);
        }
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
        return meals.stream()
                .filter(predicate)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
