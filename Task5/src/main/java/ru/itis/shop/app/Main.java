package ru.itis.shop.app;

import ru.itis.shop.infrastructure.persistence.jdbc.DriverManagerDataSource;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.jdbc.UserRepositoryJbdcImpl;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;

public class Main {


    public static void main(String[] args) {
        DataSource dataSource = new DriverManagerDataSource("jdbc:postgresql://localhost:5432/shop_db", "postgres", "trenty7");

        UserRepository userRepository = new UserRepositoryJbdcImpl(dataSource);
        UserService userService = new UserService(userRepository);

        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while(true) {
            operations.showMenu();
        }
    }
}