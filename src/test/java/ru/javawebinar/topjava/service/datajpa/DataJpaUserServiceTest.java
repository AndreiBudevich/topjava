package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getUserWithMeals() {
        User actual = service.getUserWithMeals(USER_ID);
        user.setMeals(meals);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("registered", "meals.user")
                .isEqualTo(user);
    }

    @Test
    public void getUserNotMeals() {
        User actual = service.getUserWithMeals(GUEST_ID);
        Assert.assertTrue(actual.getMeals().isEmpty());
    }

    @Test
    public void getNotFoundUser() {
        Assert.assertThrows(NotFoundException.class, () -> service.getUserWithMeals(NOT_FOUND));
    }
}
