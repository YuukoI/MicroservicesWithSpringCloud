package com.car.service.controllers;

import com.car.service.entities.Car;
import com.car.service.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> findAllCars() {
        List<Car> cars = carService.findAllCars();

        if(cars.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findCarById(@PathVariable Long id) {
        Car car = carService.findCarById(id);
        if(car == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@Valid @RequestBody Car car) {
        return ResponseEntity.ok(carService.saveCar(car));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCarById(@PathVariable Long id) {
        Car car = carService.findCarById(id);
        if(car == null){
            return ResponseEntity.noContent().build();
        }
        carService.deleteCarById(car.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@Valid @RequestBody Car car, @PathVariable Long id) {
        if(carService.findCarById(id) == null){
            return ResponseEntity.notFound().build();
        }

        car.setId(id);
        Car savedCar = carService.saveCar(car);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> findAllCarsByUserId(@PathVariable Long userId) {
        List<Car> cars = carService.findByUserId(userId);
        if(cars.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cars);
    }
}
