package ru.javawebinar.topjava.service.datajpa;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getMealWithUser() {
        Meal actual = service.getMealWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        adminMeal1.setUser(admin);
        Assertions.assertThat(actual).
                usingRecursiveComparison()
                .ignoringFields("user.meals", "user.registered")
                .isEqualTo(adminMeal1);
    }

    @Test
    public void getMealNotUser() {
        Assert.assertThrows(NotFoundException.class, () -> service.getMealWithUser(ADMIN_MEAL_ID, USER_ID));
    }

    @Test
    public void getMealNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> service.getMealWithUser(NOT_FOUND, NOT_FOUND));
    }
}





