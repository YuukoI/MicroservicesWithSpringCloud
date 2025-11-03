package com.user.service.services;

import com.user.service.entities.User;
import com.user.service.integration.Car;
import com.user.service.integration.Motorbike;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> findAllUsers();

    User findById(Long id);

    User saveUser(User user);

    void deleteUserById(Long id);

    List<Car> findAllCarsByUserId(Long userId);

    List<Motorbike> findAllMotorbikesByUserId(Long userId);

    Car saveCar(Long userId, Car car);

    Motorbike saveMotorbike(Long userId, Motorbike motorbike);

    Map<String, Object> findVehiclesByUserId(Long userId);

}
