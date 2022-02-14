package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
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
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        if (repository.get(id).getUserId() != userId.intValue()) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        if (!repository.get(id).getUserId().equals(userId)) {
            return null;
        }
        return repository.get(id);
    }

    private Comparator<Meal> sort = (meal1, meal2) -> meal2.getDate().compareTo(meal1.getDate());

    @Override
    public Collection<Meal> getAll(Integer userId) {
        Predicate<Meal> filterIdUser = meal -> userId.equals(meal.getUserId());
        return repository.values()
                .stream()
                .filter(filterIdUser)
                .sorted(sort)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilterList(LocalDate startDate, LocalDate endDate, boolean applyFilterDate, Integer userId) {
        Predicate<Meal> filterDate = meal -> {
            try {
                return DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        };
        if (!applyFilterDate) {
            filterDate = user -> true;
        }
        Predicate<Meal> filterIdUser = meal -> userId.equals(meal.getUserId());
        return repository.values()
                .stream()
                .filter(filterIdUser)
                .filter(filterDate)
                .sorted(sort)
                .collect(Collectors.toList());
    }
}

