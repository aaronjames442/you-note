package com.notes_app.dao;

import com.notes_app.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
       SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
       while (result.next()) {
           User user = mapRowToUser(result);
           users.add(user);
       }
       return users;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, username);

        if (rs.next()) {
            return Optional.ofNullable(mapRowToUser(rs)); // <- must return com.notes_app.model.User
        }
        return Optional.empty();
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
        return mapRowToUser(result);
    }

    @Override
    public User createUser(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?,?,?) RETURNING id";
        int result = jdbcTemplate.queryForObject(sql, Integer.class, user.getUsername(), user.getPassword(), user.getRole());
        user.setId(result);
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ? SET password = ? SET role = ? WHERE id = ?";
        int result = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole(), user.getId());
        return result == 1;
    }

    @Override
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int result = jdbcTemplate.update(sql, id);
        return result == 1;
    }

    private User mapRowToUser(SqlRowSet rowSet) {
        return new User(
                rowSet.getInt("id"),
                rowSet.getString("username"),
                rowSet.getString("password"),
                rowSet.getString("role")
        );

    }
}
