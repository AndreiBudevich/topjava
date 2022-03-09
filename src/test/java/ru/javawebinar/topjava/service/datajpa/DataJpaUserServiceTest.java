package ru.javawebinar.topjava.service.datajpa;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.adminMeal1;
import static ru.javawebinar.topjava.MealTestData.adminMeal2;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getUserWithMeals() {
        User actual = service.getUserWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(actual, admin);
        Assertions.assertThat(actual.getMeals())
                .containsExactly(adminMeal2, adminMeal1);
    }

    @Test
    public void getUserNotMeals() {
        User actual = service.getUserWithMeals(GUEST_ID);
        USER_MATCHER.assertMatch(actual, guest);
        Assert.assertTrue(actual.getMeals().isEmpty());
    }

    @Test
    public void getNotFoundUser() {
        Assert.assertThrows(NotFoundException.class, () -> service.getUserWithMeals(NOT_FOUND));
    }
}
