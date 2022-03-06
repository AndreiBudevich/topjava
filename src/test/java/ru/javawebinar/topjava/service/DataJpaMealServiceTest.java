package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.DATAJPA, Profiles.DATAJPA_JPA})
public class DataJpaMealServiceTest extends MealServiceTest {
}
