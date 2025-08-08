package com.car.service.services;

import com.car.service.entities.Car;

import java.util.List;

public interface CarService {

    List<Car> findAllCars();

    Car findCarById(Long id);

    Car saveCar(Car car);

    Car updateCar(Car car);

    void deleteCarById(Long id);

    List<Car> findByUserId(Long id);
}
