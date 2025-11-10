package com.user.service.services;

import com.user.service.entities.User;
import com.user.service.feignClients.CarFeignClient;
import com.user.service.feignClients.MotorbikeFeignClient;
import com.user.service.integration.Car;
import com.user.service.integration.Motorbike;
import com.user.service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final CarFeignClient carFeignClient;

    private final MotorbikeFeignClient motorbikeFeignClient;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Car> findAllCarsByUserId(Long userId) {
        return carFeignClient.getCars(userId);
    }

    @Override
    public List<Motorbike> findAllMotorbikesByUserId(Long userId) {
        return motorbikeFeignClient.getMotorbikes(userId);
    }

    @Override
    public Car saveCar(Long userId, Car car) {
        car.setUserId(userId);
        return carFeignClient.saveCar(car);
    }

    @Override
    public Motorbike saveMotorbike(Long userId, Motorbike motorbike) {
        motorbike.setUserId(userId);
        return motorbikeFeignClient.saveMotorbike(motorbike);
    }

    @Override
    public Map<String, Object> findVehiclesByUserId(Long userId) {
        Map<String, Object> map = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            map.put("error", "User not found");
        } else {
            map.put("user", user);
        }

        List<Car> cars = findAllCarsByUserId(userId);
        if (cars.isEmpty()) {
            map.put("error", "Cars not found");
        } else {
            map.put("cars", cars);
        }

        List<Motorbike> motorbikes = findAllMotorbikesByUserId(userId);
        if (motorbikes.isEmpty()) {
            map.put("error", "Motorbikes not found");
        } else {
            map.put("motorbikes", motorbikes);
        }

        return map;
    }
}