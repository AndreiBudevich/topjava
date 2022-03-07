package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.DATAJPA, Profiles.DATAJPA_JPA})
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getUserWithMeals() {
        User actual = service.getUserWithMeals(USER_ID);
        user.setMeals(meals);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("registered")
                .ignoringFieldsMatchingRegexes(".*user")
                .isEqualTo(user);
    }
}
