package com.car.service.services;

import com.car.service.entities.Car;
import com.car.service.repositories.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarServiceImp implements CarService {

    private final CarRepository carRepository;

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> findByUserId(Long id) {
        return carRepository.findByUserId(id);
    }

    @Override
    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car findCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Car car) {
        return carRepository.save(car);
    }
}
