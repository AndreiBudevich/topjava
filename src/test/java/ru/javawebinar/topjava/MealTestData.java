package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int ID_1 = 100003;
    public static final int ID_2 = 100004;
    public static final int ID_NOT_FOUND = 10;

    public static final Meal MEAL_ID_1 = new Meal(100003,LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_ID_2 = new Meal(100004,LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_ID_3 = new Meal(100005,LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_ID_4 = new Meal(100006,LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_ID_5 = new Meal(100007,LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_ID_6 = new Meal(100008,LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_ID_7 = new Meal(100009,LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(1986, Month.APRIL, 2, 7, 0), "Завтрак", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_ID_7);
        updated.setDateTime(LocalDateTime.of(2012, Month.JULY, 26, 3, 5));
        updated.setDescription("Перекус");
        updated.setCalories(777);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}