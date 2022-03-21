package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CollectorUserRole implements Collector<User, Map<Integer, User>, List<User>> {
    public static CollectorUserRole toList() {
        return new CollectorUserRole();
    }

    @Override
    public Supplier<Map<Integer, User>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<Integer, User>, User> accumulator() {
        Collection<Role> roles = new HashSet<>();
        return (map, user) ->
                map.merge(user.id(), user, (user1, user2) -> {
                    roles.addAll(user1.getRoles());
                    roles.addAll(user2.getRoles());
                    user1.setRoles(roles);
                    return user1;
                });
    }

    @Override
    public BinaryOperator<Map<Integer, User>> combiner() {
        return (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        };
    }

    @Override
    public Function<Map<Integer, User>, List<User>> finisher() {
        return map -> map.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .toList();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}