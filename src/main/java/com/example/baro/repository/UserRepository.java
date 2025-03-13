package com.example.baro.repository;

import com.example.baro.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final List<User> users;

    public boolean existByUsername(String username) {

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    public Long findNextId() {

        return users.isEmpty() ? 1L : users.get(users.size() - 1).getId() + 1;
    }

    public User save(User user) {

        users.add(user);

        return users.get(users.size() - 1);
    }
}
