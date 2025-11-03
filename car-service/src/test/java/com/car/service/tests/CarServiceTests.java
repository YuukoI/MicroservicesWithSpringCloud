package com.car.service.tests;

import com.car.service.entities.Car;
import com.car.service.services.CarServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTests {

    @Mock
    private CarServiceImp carServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllCars() {
        List<Car> cars = List.of(
                new Car(1L, "Toyota", "Corolla", "ABC123", 10),
                new Car(2L, "Honda", "Civic", "XYZ789", 20)
        );
        when(carServiceImp.findAllCars()).thenReturn(cars);

        List<Car> result = carServiceImp.findAllCars();

        assertEquals(2, result.size());
        verify(carServiceImp).findAllCars();
    }

    @Test
    void testFindCarById() {
        Car car = new Car(1L, "Toyota", "Corolla", "ABC123", 10);
        when(carServiceImp.findCarById(1L)).thenReturn(car);

        Car result = carServiceImp.findCarById(1L);

        assertNotNull(result);
        assertEquals("Toyota", result.getBrand());
        assertEquals("Corolla", result.getModel());
        assertEquals("ABC123", result.getPlate());
        assertEquals(10, result.getUserId());
        verify(carServiceImp).findCarById(1L);
    }

    @Test
    void testSaveCar() {
        Car car = new Car(null, "Toyota", "Corolla", "ABC123", 10);
        Car savedCar = new Car(1L, "Toyota", "Corolla", "ABC123", 10);
        when(carServiceImp.saveCar(car)).thenReturn(savedCar);

        Car result = carServiceImp.saveCar(car);

        assertNotNull(result.getId());
        assertEquals("Toyota", result.getBrand());
        verify(carServiceImp).saveCar(car);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carServiceImp).deleteCarById(1L);

        carServiceImp.deleteCarById(1L);

        verify(carServiceImp).deleteCarById(1L);
    }

    @Test
    void testFindByUserId() {
        List<Car> cars = List.of(new Car(1L, "Toyota", "Corolla", "ABC123", 10));
        when(carServiceImp.findByUserId(10L)).thenReturn(cars);

        List<Car> result = carServiceImp.findByUserId(10L);

        assertFalse(result.isEmpty());
        verify(carServiceImp).findByUserId(10L);
    }
}
