package com.user.service.feignClients;

import com.user.service.integration.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CAR-SERVICE")
public interface CarFeignClient {

    @PostMapping("/cars")
    public Car saveCar(@RequestBody Car car);

    @GetMapping("/cars/user/{userId}")
    public List<Car> getCars(@PathVariable("userId") Long userId);
}
