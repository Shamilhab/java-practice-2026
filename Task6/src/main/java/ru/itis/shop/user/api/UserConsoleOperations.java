package ru.itis.shop.user.api;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1":{
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3": {
                findById();
            }
            break;
            case "4": {
                updateProfileDescription();
            }
            break;
            case "5": {
                printAll();
            }
            break;
            case "6": {
                printAllByProfileDescription();
            }
            break;
            case  "0":{
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить данные пользователя");
        System.out.println("5. Показать всех пользователей");
        System.out.println("6. Показать всех пользователей c одинаковым профилем");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(name, email, password, profileDescription);
    }

    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Email или пароль не верны");
        }
    }

    private void findById() {
        System.out.println("Введите id:");
        Integer id = Integer.parseInt(scanner.nextLine());

        Optional<UserDto> userDto = userService.findById(id);

        if (userDto.isPresent()) {
            System.out.println("Email пользователя: " + userDto.get().getEmail());
        } else {
            System.out.println("Пользователь не нашелся");
        }
    }


    private void updateProfileDescription() {
        System.out.println("Введите email:");
        String email = scanner.nextLine();

        if (userService.findByEmail(email).isPresent()) {
            System.out.println("Пользователь найден, вы можете обновить описание профиля.");
            System.out.println("Введите новое описание профиля:");
            String newProfileDescription = scanner.nextLine();

            userService.updateProfileDescription(email, newProfileDescription);

            System.out.println("Профиль успешно обновлен");
        } else {
            System.out.println("Пользователь не найден");
        }
    }

    private void printAll() {
        List<UserDto> userDtos = userService.findAll();
        for (UserDto userDto : userDtos) {
            System.out.println(userDto.getName() + " " + userDto.getEmail());
        }

        if  (userDtos.isEmpty()) {
            System.out.println("Пользователей не существует");
        }
    }

    private void printAllByProfileDescription() {
        String profileDescription = scanner.nextLine();

        List<UserDto> listUserDto = userService.findAllByProfileDescription(profileDescription);
        for (UserDto userDto : listUserDto) {
            System.out.println(userDto.getName() + " " + userDto.getEmail());
        }

        if  (listUserDto.isEmpty()) {
            System.out.println("Такие пользователи не нашлись");
        }
    }
}