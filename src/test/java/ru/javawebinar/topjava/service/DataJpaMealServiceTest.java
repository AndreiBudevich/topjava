package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;;import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.admin;

@ActiveProfiles({Profiles.DATAJPA, Profiles.DATAJPA_JPA})
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getMealWithUser() {
        Meal actual = service.getMealWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        adminMeal1.setUser(admin);
        assertThat(actual).usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes(".*meals", ".*registered")
                .isEqualTo(adminMeal1);
    }
}