package ru.itmo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.itmo.api.model.Gender;
import ru.itmo.api.model.Status;
import ru.itmo.api.model.User;

@Getter
@AllArgsConstructor
public class UserDTO {

    private final Integer id;
    private final String name;
    private final String email;
    private final Gender gender;
    private final Status status;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.status = user.getStatus();
    }
}
