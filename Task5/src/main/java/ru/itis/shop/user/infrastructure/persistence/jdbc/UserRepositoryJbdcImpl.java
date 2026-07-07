package ru.itis.shop.user.infrastructure.persistence.jdbc;

import ru.itis.shop.infrastructure.persistence.jdbc.RowMapper;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJbdcImpl implements UserRepository {
    private final DataSource dataSource;
    private final RowMapper<User> userMapper = row -> new User(
            row.getInt("id"),
            row.getString("name"),
            row.getString("email"),
            row.getString("password"),
            row.getString("profileDescription")
    );

    public UserRepositoryJbdcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User user) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public void updateProfileDescription(String email, String newProfileDescription) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public Optional<User> findByEmail(String email) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public Optional<User> findById(Integer id) {
        throw new RuntimeException("Not implemented");

    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()){
            try (Statement statement = connection.createStatement()){
                try (ResultSet resultSet = statement.executeQuery("select * from account")) {
                    while (resultSet.next()) {
                        users.add(userMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

    @Override
    public List<User> findAllByProfileDescription(String profileDescription) {
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()){
            try (Statement statement = connection.createStatement()){
                // делаю запрос на соответствие по профилю
                try (ResultSet resultSet = statement.executeQuery("select * from account where profileDescription=" + "'" + profileDescription + "'")) {
                    while (resultSet.next()) {
                        users.add(userMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

}