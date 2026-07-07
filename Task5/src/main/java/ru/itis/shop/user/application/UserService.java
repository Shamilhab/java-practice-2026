package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

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

    public boolean isUserPresentByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<String> getEmailById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return Optional.of(userOptional.get().getEmail());
        } else return Optional.empty();
    }

    public void updateProfileDescription(String email, String newProfileDescription) {
        userRepository.updateProfileDescription(email, newProfileDescription);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllByProfileDescription(String profileDescription) {
        return userRepository.findAllByProfileDescription(profileDescription);
    }
}