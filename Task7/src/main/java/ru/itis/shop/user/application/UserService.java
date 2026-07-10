package ru.itis.shop.user.application;

import ru.itis.shop.user.api.dto.UserDto;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void signUp(String name, String email, String password, String profileDescription) {
        User user = new User(name, email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().equals(password);
        } else return false;
    }

    public Optional<UserDto> findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            UserDto userDto = new UserDto(userOptional.get());

            return Optional.of(userDto);
        } else return Optional.empty();
    }

    public Optional<UserDto> findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            UserDto userDto = new UserDto(userOptional.get());

            return Optional.of(userDto);
        } else return Optional.empty();
    }


    public void updateProfileDescription(String email, String newProfileDescription) {
        userRepository.updateProfileDescription(email, newProfileDescription);
    }

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(new UserDto(user));
        }

        return userDtos;
    }

    public List<UserDto> findAllByProfileDescription(String profileDescription) {
        List<User> users = userRepository.findAllByProfileDescription(profileDescription);
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(new UserDto(user));
        }

        return userDtos;
    }
}