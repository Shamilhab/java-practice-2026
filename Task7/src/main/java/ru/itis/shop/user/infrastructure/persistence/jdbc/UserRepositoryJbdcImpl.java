package ru.itis.shop.user.infrastructure.persistence.jdbc;

import ru.itis.shop.infrastructure.persistence.jdbc.RowMapper;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
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
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("insert into account(name, email, password, profiledescription) values (?, ?, ?, ?)")) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getProfileDescription());

                int affectedRowsCount = preparedStatement.executeUpdate();
                if (affectedRowsCount != 1) {
                    throw new RuntimeException("Произошла ошибка. Количество вставленных строк " + affectedRowsCount);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void updateProfileDescription(String email, String newProfileDescription) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("update account set profileDescription = ? where email = ?")) {
                preparedStatement.setString(1, newProfileDescription);
                preparedStatement.setString(2, email);

                int affectedRowsCount = preparedStatement.executeUpdate();
                if (affectedRowsCount != 1) {
                    throw new RuntimeException("Произошла ошибка. Количество вставленных строк " + affectedRowsCount);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select * from account where email = ?")) {
                preparedStatement.setString(1, email);

                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(userMapper.mapRow(resultSet));
                    }
                }

            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Integer id) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select * from account where id = ?")) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return Optional.of(userMapper.mapRow(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
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