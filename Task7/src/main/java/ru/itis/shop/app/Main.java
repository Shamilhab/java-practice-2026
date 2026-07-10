package ru.itis.shop.app;

import ru.itis.shop.infrastructure.persistence.jdbc.DriverManagerDataSource;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.jdbc.UserRepositoryJbdcImpl;
import ru.itis.shop.user.repository.UserRepository;
import ru.itis.shop.util.PropertiesReader;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        PropertiesReader propertiesReader = new PropertiesReader("application.properties");

        Properties properties = propertiesReader.loadProperties();

        DataSource dataSource = new DriverManagerDataSource(properties.getProperty("dbUrl"), properties.getProperty("dbUser"), properties.getProperty("dbPassword"));

        UserRepository userRepository = new UserRepositoryJbdcImpl(dataSource);
        UserService userService = new UserService(userRepository);

        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
