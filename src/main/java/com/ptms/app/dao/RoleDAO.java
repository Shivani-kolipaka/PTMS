package com.ptms.app.dao.RoleDAO;

import com.ptms.app.model.Role;
import com.ptms.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    // CREATE
    public Role create(Role role) throws SQLException {

        String sql = "INSERT INTO roles (role_name) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, role.getRoleName());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {

                if (resultSet.next()) {
                    role.setId(resultSet.getInt(1));
                }
            }
        }

        return role;
    }

    // READ - find by ID
    public Role findById(int id) throws SQLException {

        String sql =
                "SELECT id, role_name FROM roles WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Role role = new Role();

                    role.setId(resultSet.getInt("id"));
                    role.setRoleName(
                            resultSet.getString("role_name")
                    );

                    return role;
                }
            }
        }

        return null;
    }

    // READ - find all
    public List<Role> findAll() throws SQLException {

        List<Role> roles = new ArrayList<>();

        String sql =
                "SELECT id, role_name FROM roles";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Role role = new Role();

                role.setId(resultSet.getInt("id"));
                role.setRoleName(
                        resultSet.getString("role_name")
                );

                roles.add(role);
            }
        }

        return roles;
    }

    // UPDATE
    public boolean update(Role role) throws SQLException {

        String sql =
                "UPDATE roles SET role_name = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, role.getRoleName());
            statement.setInt(2, role.getId());

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean delete(int id) throws SQLException {

        String sql =
                "DELETE FROM roles WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        }
    }
}
