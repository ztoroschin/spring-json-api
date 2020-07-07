package ru.itmo.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.api.exception.BadRequestException;
import ru.itmo.api.repository.UserRepository;
import ru.itmo.api.model.Gender;
import ru.itmo.api.model.Status;
import ru.itmo.api.model.User;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public int addUser(String name, String email, String gender, String status) {
        // TODO: check existence of name, email
        User user = new User(null, name, email, Gender.valueOf(gender.toUpperCase()), Status.valueOf(status.toUpperCase()));
        return userRepository.save(user).getId();
    }

    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUserById(int id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public boolean updateUserById(int id, String name, String email, String gender) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        User userFromDb = userRepository.findById(id).get();
        if (name != null) {
            userFromDb.setName(name);
        }
        if (email != null) {
            userFromDb.setEmail(email);
        }
        if (gender != null) {
            try {
                userFromDb.setGender(Gender.valueOf(gender.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new BadRequestException(e.getMessage());
            }
        }
        userRepository.save(userFromDb);
        return true;
    }

}
