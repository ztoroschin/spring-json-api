package ru.itmo.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.api.dto.UserDTO;
import ru.itmo.api.service.UserService;
import ru.itmo.api.exception.BadRequestException;
import ru.itmo.api.exception.ResourceNotFoundException;
import ru.itmo.api.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int addUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("gender") String gender,
            @RequestParam("status") String status
    ) {
        try {
            return userService.addUser(name, email, gender, status);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(String.format("Error in some field: %s", e.getMessage()));
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        List<UserDTO> users = new ArrayList<>();
        for (var user : userService.getUsers()) {
            users.add(new UserDTO(user));
        }
        return users;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable("id") int id) {
        Optional<User> user = userService.getUser(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Can not find user with id %d", id));
        }
        return new UserDTO(user.get());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable int id) {
        if (!userService.deleteUserById(id)) {
            throw new ResourceNotFoundException(String.format("Can not find user with id %d", id));
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateUser(
            @PathVariable int id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "gender", required = false) String gender) {
        if (!userService.updateUserById(id, name, email, gender)) {
            throw new ResourceNotFoundException(String.format("Can not find user with id %d", id));
        }
    }

}
