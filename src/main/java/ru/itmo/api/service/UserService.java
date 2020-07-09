package ru.itmo.api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.api.exception.BadRequestException;
import ru.itmo.api.exception.ResourceNotFoundException;
import ru.itmo.api.model.Gender;
import ru.itmo.api.model.StatisticsEntry;
import ru.itmo.api.model.Status;
import ru.itmo.api.model.User;
import ru.itmo.api.repository.StatisticsRepository;
import ru.itmo.api.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final StatisticsRepository statisticsRepository;

    public int addUser(String name, String email, String gender, String status) {
        User user = new User(null, name, email, Gender.valueOf(gender.toUpperCase()), Status.valueOf(status.toUpperCase()));
        return userRepository.save(user).getId();
    }

    public User getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Can not find user with id %d", id));
        }
        return user.get();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(int id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Can not find user with id %d", id));
        }
        userRepository.deleteById(id);
    }

    public void updateUserById(int id, String name, String email, String gender) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Can not find user with id %d", id));
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
    }

    public void changeUserStatus(Integer id, String status) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Can not find user");
        }
        if (status == null) {
            throw new BadRequestException("Status must not be void");
        }
        User userFromDb = userRepository.findById(id).get();
        try {
            userFromDb.setStatus(Status.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
        userRepository.save(userFromDb);

        Status statusEnum;
        try {
            statusEnum = Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e.getMessage());
        }
        StatisticsEntry statisticsEntry = new StatisticsEntry(id, statusEnum);
        statisticsRepository.save(statisticsEntry);
    }

}
