package com.ptms.app.dao;

import com.ptms.app.model.User;
import com.ptms.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // CREATE
    public User create(User user) throws SQLException {

        String sql = "INSERT INTO users " +
                "(name, email, password_hash, role_id) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setInt(4, user.getRoleId());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
            }
        }

        return user;
    }

    // READ - by ID
    public User findById(int id) throws SQLException {

        String sql = "SELECT id, name, email, password_hash, role_id " +
                "FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    User user = new User();

                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPasswordHash(
                            resultSet.getString("password_hash"));
                    user.setRoleId(resultSet.getInt("role_id"));

                    return user;
                }
            }
        }

        return null;
    }

    // READ - all
    public List<User> findAll() throws SQLException {

        List<User> users = new ArrayList<>();

        String sql = "SELECT id, name, email, password_hash, role_id " +
                "FROM users";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPasswordHash(
                        resultSet.getString("password_hash"));
                user.setRoleId(resultSet.getInt("role_id"));

                users.add(user);
            }
        }

        return users;
    }

    // UPDATE
    public boolean update(User user) throws SQLException {

        String sql = "UPDATE users SET " +
                "name = ?, email = ?, password_hash = ?, role_id = ? " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setInt(4, user.getRoleId());
            statement.setInt(5, user.getId());

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean delete(int id) throws SQLException {

        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        }
    }
}