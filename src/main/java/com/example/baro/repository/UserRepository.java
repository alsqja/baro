package com.example.baro.repository;

import com.example.baro.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private final Map<String, Long> usernameIndex = new HashMap<>();
    private Long id;

    public boolean existByUsername(String username) {

        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    public Long findNextId() {

        return id == null ? 1L : id + 1;
    }

    public User save(User user) {

        id = user.getId();
        users.put(user.getId(), user);
        usernameIndex.put(user.getUsername(), user.getId());

        return users.get(id);
    }

    public Optional<User> findByUsername(String username) {

        Long id = usernameIndex.get(username);

        return id == null ? Optional.empty() : Optional.of(users.get(id));
    }
}
