package com.apress.users;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    private Map<String, User> users = new HashMap<>() {
        {
            put("ximena@email.com", new User("ximena@email.com", "Ximena"));
            put("norma@email.com", new User("norma@email.com", "Norma"));
        }
    };

    @GetMapping
    public Collection<User> getAll() {
        return users.values();
    }

    @GetMapping("/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return users.get(email);
    }

    @PostMapping
    public User save(@RequestBody User user) {
        users.put(user.getEmail(), user);
        return user;
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable String email) {
        users.remove(email);
    }
}
