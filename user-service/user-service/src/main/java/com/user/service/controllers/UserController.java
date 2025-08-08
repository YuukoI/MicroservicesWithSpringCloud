package com.user.service.controllers;

import com.user.service.entities.User;
import com.user.service.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Long userId) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") Long userId) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUserById(userId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @Valid @RequestBody User user) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.notFound().build();
        }

        user.setId(userId);
        User updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

}
