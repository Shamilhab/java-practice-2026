package ru.itis.shop.app;

import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.UserMapper;
import ru.itis.shop.user.infrastructure.persistence.UserRepositoryJbdcImpl;

public class Main {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/users";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "trenty7";

    public static void main(String[] args) {

        UserMapper userMapper = new UserMapper();
        UserRepositoryJbdcImpl userDbRepository = new UserRepositoryJbdcImpl(DB_URL, DB_USER, DB_PASSWORD);
        UserService userService = new UserService(userDbRepository);

        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while(true) {
            operations.showMenu();
        }
    }
}