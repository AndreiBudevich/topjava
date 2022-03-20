package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static ru.javawebinar.topjava.repository.jdbc.JdbcUserRepository.Mapper.getMapper;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        }
        batchUpdate(user);
        return user;
    }

    public void batchUpdate(User user) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", user.getId());
        List<Role> roles = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) values (?, ?)", new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                ps.setInt(1, user.id());
                ps.setString(2, getStringRoles(roles.get(i)));
            }

            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    private static String getStringRoles(Role role) {
        return role.equals(Role.USER) ? "USER" : "ADMIN";
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id WHERE id=?", getMapper(), id);
        return DataAccessUtils.singleResult(getCollect(users));
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id WHERE email=?", getMapper(), email);
        return DataAccessUtils.singleResult(getCollect(users));
    }

    @Override
    public List<User> getAll() {
        List<User> query = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id", getMapper());
        return getCollect(query);
    }

    private List<User> getCollect(List<User> users) {
        return users.stream().collect(CollectorRole.toList());
    }

    public static class CollectorRole implements Collector<User, Map<Integer, User>, List<User>> {
        public static CollectorRole toList() {
            return new CollectorRole();
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

    static class Mapper implements RowMapper<User> {

        private Mapper() {
        }

        private static class MapperHolder {
            public static final Mapper HOLDER_INSTANCE = new Mapper();
        }

        public static Mapper getMapper() {
            return MapperHolder.HOLDER_INSTANCE;
        }

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRegistered(rs.getDate("registered"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setCaloriesPerDay(rs.getInt("calories_per_day"));
            String role = rs.getString("role");
            if (role != null) {
                user.setRoles(getRoles(role));
            } else {
                user.setRoles(new TreeSet<>());
            }
            return user;
        }

        private static Collection<Role> getRoles(String roleName) {
            return roleName.equals(Role.USER.name()) ? List.of(Role.USER) : List.of(Role.ADMIN);
        }
    }
}

