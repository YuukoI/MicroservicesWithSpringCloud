package com.user.service.controllers;

import com.user.service.entities.User;
import com.user.service.integration.Car;
import com.user.service.integration.Motorbike;
import com.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
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

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> findAllCarsByUserId(@PathVariable("userId") Long userId) {
        User user = userService.findById(userId);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        List<Car> cars = userService.findAllCarsByUserId(userId);

        if (cars.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "motorbikesCB", fallbackMethod = "fallbackGetMotorbikes")
    @GetMapping("/motorbikes/{userId}")
    public ResponseEntity<List<Motorbike>> findAllMotorbikesByUserId(@PathVariable("userId") Long userId) {
        User user = userService.findById(userId);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        List<Motorbike> motorbikes = userService.findAllMotorbikesByUserId(userId);

        if (motorbikes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(motorbikes);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackSaveCar")
    @PostMapping("/cars/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") Long userId, @RequestBody Car car) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.saveCar(userId, car));
    }

    @CircuitBreaker(name = "motorbikesCB", fallbackMethod = "fallbackSaveMotorbike")
    @PostMapping("/motorbikes/{userId}")
    public ResponseEntity<Motorbike> saveMotorbike(@PathVariable("userId") Long userId, @RequestBody Motorbike motorbike) {
        if (userService.findById(userId) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userService.saveMotorbike(userId, motorbike));
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallbackGetAll")
    @GetMapping("/vehicles/{userId}")
    public ResponseEntity<Map<String, Object>> findAllVehiclesByUserId(@PathVariable("userId") Long userId){
        Map<String, Object> map = userService.findVehiclesByUserId(userId);

        return ResponseEntity.ok(map);
    }

    private ResponseEntity<?> fallbackGetCars(@PathVariable("userId") Long userId, RuntimeException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "⚠️ Service unavailable, executing fallbackGetCar for userId: " + userId);
        response.put("error", exception.getMessage());
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<?>  fallbackGetMotorbikes(@PathVariable("userId") Long userId, RuntimeException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "⚠️ Service unavailable, executing fallbackGetMotorbike for userId: " + userId);
        response.put("error", exception.getMessage());
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<?> fallbackSaveCar(@PathVariable("userId") Long userId, @RequestBody Car car, RuntimeException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "⚠️ Service unavailable, executing fallbackSaveCar for userId: " + userId);
        response.put("error", exception.getMessage());
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<?> fallbackSaveMotorbike(@PathVariable("userId") Long userId, @RequestBody Motorbike motorbike, RuntimeException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "⚠️ Service unavailable, executing fallbackSaveMotorbike for userId: " + userId);
        response.put("error", exception.getMessage());
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Map<String, Object>> fallbackGetAll(@PathVariable("userId") Long userId, RuntimeException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "⚠️ Service unavailable, executing fallbackGetAll for userId: " + userId);
        response.put("error", exception.getMessage());
        return ResponseEntity.ok(response);
    }

}
