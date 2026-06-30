package ru.itis.shop.app;

import Task1.ru.itis.shop.user.api.UserConsoleOperations;
import Task1.ru.itis.shop.user.infrastucture.persistence.UserDatabaseRepository;
import Task1.ru.itis.shop.user.infrastucture.persistence.UserFileRepository;

public class Main {
    public static void main(String[] args) {
        UserFileRepository userFileRepository = new UserFileRepository("users.txt");
        UserDatabaseRepository userDatabaseRepository = new UserDatabaseRepository();
        UserConsoleOperations operations = new UserConsoleOperations(userFileRepository);

        while (true) {
            operations.showMenu();
        }
    }
}
