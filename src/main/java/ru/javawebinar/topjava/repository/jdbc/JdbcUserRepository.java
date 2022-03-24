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
import ru.javawebinar.topjava.util.CollectorUserRole;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

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
        ValidationUtil.validationEntity(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            int update = namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password, 
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource);
            if (update != 0) {
                jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", user.getId());
            } else {
                return null;
            }
        }
        batchUpdate(user);
        return user;
    }

    public void batchUpdate(User user) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        if (roles.size() > 0) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) values (?, ?)", new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i)
                        throws SQLException {
                    ps.setInt(1, user.id());
                    ps.setString(2, roles.get(i).name());
                }

                public int getBatchSize() {
                    return roles.size();
                }
            });
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id WHERE id=?", MAPPER, id);
        return DataAccessUtils.singleResult(getCollectedUsers(users));
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id WHERE email=?", MAPPER, email);
        return DataAccessUtils.singleResult(getCollectedUsers(users));
    }

    @Override
    public List<User> getAll() {
        List<User> query = jdbcTemplate.query("SELECT * FROM users u  LEFT JOIN user_roles ur ON u.id = ur.user_id ORDER BY u.name, u.email", MAPPER);
        return getCollectedUsers(query);
    }

    private List<User> getCollectedUsers(List<User> users) {
        return users.stream().collect(CollectorUserRole.toList());
    }

    private static final RowMapper<User> MAPPER = (rs, rowNum) -> {
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
            user.setRoles(EnumSet.of(Role.valueOf(role)));
        } else {
            user.setRoles(new HashSet<>());
        }
        return user;
    };
}


