package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 2), 2);
        save(new Meal(LocalDateTime.of(2022, Month.JANUARY, 30, 13, 0), "Обед", 1000, 2), 2);
        save(new Meal(LocalDateTime.of(2022, Month.JANUARY, 30, 20, 0), "Ужин", 500, 2), 2);
        save(new Meal(LocalDateTime.of(2022, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 2), 2);
        save(new Meal(LocalDateTime.of(2022, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 2), 2);
        save(new Meal(LocalDateTime.of(2022, Month.JANUARY, 31, 13, 0), "Обед", 500, 2), 2);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 15, 20, 0), "Ужин", 410, 2), 2);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 15, 10, 0), "Картошка", 800, 1), 1);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 13, 13, 0), "спагетти", 900, 1), 1);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 14, 14, 0), "мясо", 1200, 1), 1);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 14, 15, 0), "суши", 1100, 1), 1);
        save(new Meal(LocalDateTime.of(2022, Month.FEBRUARY, 14, 16, 0), "курица", 950, 1), 1);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUserId(userId);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        if (get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUserId(userId);
        return repository.compute(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal;
        if ((meal = repository.get(id)) == null || meal.getUserId() != userId) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal;
        if ((meal = repository.get(id)) == null || meal.getUserId() != userId) {
            return null;
        }
        return meal;
    }

    private final Comparator<Meal> sort = (meal1, meal2) -> meal2.getDate().compareTo(meal1.getDate());

    @Override
    public List<Meal> getAll(int userId) {
        return filter(meal -> true, sort, userId);
    }

    @Override
    public List<Meal> getFilterList(LocalDate startDate, LocalDate endDate, int userId) {
        return filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate), sort, userId);
    }

    private List<Meal> filter(Predicate<Meal> filterDate, Comparator<Meal> sort, int userId) {
        return repository.values()
                .stream()
                .filter(meal -> userId == meal.getUserId())
                .filter(filterDate)
                .sorted(sort)
                .collect(Collectors.toList());
    }
}
