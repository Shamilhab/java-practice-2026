package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepositoryJbdcImpl implements UserRepository {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public UserRepositoryJbdcImpl(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    @Override
    public void save(User user) {}

    @Override
    public void updateProfileDescription(String email, String newProfileDescription) {}

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            try (Statement statement = connection.createStatement()) {
                try(ResultSet resultSet = statement.executeQuery("select * from users")) {
                    while (resultSet.next()) {
                        User user = new User(
                                resultSet.getString("id"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("profileDescription")
                        );

                        users.add(user);
                    }
                }
            }
            return users;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}