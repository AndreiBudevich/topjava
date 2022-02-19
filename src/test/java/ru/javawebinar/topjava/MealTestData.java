package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int ID_1 = START_SEQ + 3;
    public static final int ID_2 = START_SEQ + 4;
    public static final int ID_NOT_FOUND = 10;

    public static final Meal meal1 = new Meal(ID_1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак юзер 1", 500);
    public static final Meal meal2 = new Meal(ID_2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед юзер 1", 1000);
    public static final Meal meal3 = new Meal(START_SEQ + 5, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин юзер 1", 500);
    public static final Meal meal4 = new Meal(START_SEQ + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение юзер 1", 100);
    public static final Meal meal5 = new Meal(START_SEQ + 7, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак юзер 1", 1000);
    public static final Meal meal6 = new Meal(START_SEQ + 8, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед юзер 1", 500);
    public static final Meal meal7 = new Meal(START_SEQ + 9, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин юзер 1", 410);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(1986, Month.APRIL, 2, 7, 0), "Завтрак", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal7);
        updated.setDateTime(LocalDateTime.of(2012, Month.JULY, 26, 3, 5));
        updated.setDescription("Перекус");
        updated.setCalories(777);
        return updated;
    }

    public static Meal getUpdatedNotFoundMeal() {
        return new Meal(1, LocalDateTime.of(1986, Month.APRIL, 2, 7, 0), "Завтрак", 1000);
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