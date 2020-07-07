package ru.itmo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.api.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
