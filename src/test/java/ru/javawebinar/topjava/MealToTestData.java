package ru.javawebinar.topjava;

import ru.javawebinar.topjava.to.MealTo;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;

public class MealToTestData {
    public static final MatcherFactory.Matcher<MealTo> MEAL_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MealTo.class);

    public static final MealTo mealTo2 = new MealTo(MEAL1_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, false);
    public static final MealTo mealTo6 = new MealTo(MEAL1_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000, true);

}
