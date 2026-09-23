package com.ptms.app.dao;

import com.ptms.app.model.Client;
import com.ptms.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    // CREATE
    public Client create(Client client) throws SQLException {

        String sql = "INSERT INTO clients " +
                "(name, email, phone, company_name) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());
            statement.setString(4, client.getCompanyName());

            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    client.setId(resultSet.getInt(1));
                }
            }
        }

        return client;
    }

    // READ - by ID
    public Client findById(int id) throws SQLException {

        String sql = "SELECT id, name, email, phone, company_name " +
                "FROM clients WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Client client = new Client();

                    client.setId(resultSet.getInt("id"));
                    client.setName(resultSet.getString("name"));
                    client.setEmail(resultSet.getString("email"));
                    client.setPhone(resultSet.getString("phone"));
                    client.setCompanyName(
                            resultSet.getString("company_name"));

                    return client;
                }
            }
        }

        return null;
    }

    // READ - all
    public List<Client> findAll() throws SQLException {

        List<Client> clients = new ArrayList<>();

        String sql = "SELECT id, name, email, phone, company_name " +
                "FROM clients";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Client client = new Client();

                client.setId(resultSet.getInt("id"));
                client.setName(resultSet.getString("name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(resultSet.getString("phone"));
                client.setCompanyName(
                        resultSet.getString("company_name"));

                clients.add(client);
            }
        }

        return clients;
    }

    // UPDATE
    public boolean update(Client client) throws SQLException {

        String sql = "UPDATE clients SET " +
                "name = ?, email = ?, phone = ?, company_name = ? " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getPhone());
            statement.setString(4, client.getCompanyName());
            statement.setInt(5, client.getId());

            return statement.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean delete(int id) throws SQLException {

        String sql = "DELETE FROM clients WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        }
    }
}
