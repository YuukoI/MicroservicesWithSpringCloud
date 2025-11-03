package com.car.service.tests;

import com.car.service.controllers.CarController;
import com.car.service.entities.Car;
import com.car.service.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarControllerTests {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @Mock
    private CarController carController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        carController = new CarController(carService);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void testFindAllCars_Found() throws Exception {
        List<Car> cars = List.of(
                new Car(1L, "Ford", "Focus", "ABC123", 10),
                new Car(2L, "Toyota", "Corolla", "XYZ789", 20)
        );

        when(carService.findAllCars()).thenReturn(cars);

        mockMvc.perform(get("/car")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(cars.size()))
                .andExpect(jsonPath("$[0].brand").value("Ford"))
                .andExpect(jsonPath("$[1].model").value("Corolla"));
    }

    @Test
    public void testFindAllCars_NotFound() throws Exception {
        when(carService.findAllCars()).thenReturn(List.of());

        mockMvc.perform(get("/car")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindCarById_Found() throws Exception {
        Long carId = 1L;
        Car car = new Car(carId, "Ford", "Focus", "ABC123", 10);

        when(carService.findCarById(carId)).thenReturn(car);

        mockMvc.perform(get("/car/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carId))
                .andExpect(jsonPath("$.brand").value("Ford"))
                .andExpect(jsonPath("$.plate").value("ABC123"));
    }

    @Test
    public void testFindCarById_NotFound() throws Exception {
        Long carId = 5L;
        when(carService.findCarById(carId)).thenReturn(null);

        mockMvc.perform(get("/car/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).findCarById(carId);
    }

    @Test
    public void testCreateCar() throws Exception {
        Car carToCreate = new Car(null, "Ford", "Focus", "ABC123", 10);
        Car savedCar = new Car(1L, "Ford", "Focus", "ABC123", 10);

        when(carService.saveCar(any(Car.class))).thenReturn(savedCar);

        String carJson = """
                {
                    "brand": "Ford",
                    "model": "Focus",
                    "plate": "ABC123",
                    "userId": 10
                }
                """;

        mockMvc.perform(post("/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.brand").value("Ford"))
                .andExpect(jsonPath("$.userId").value(10));

        verify(carService, times(1)).saveCar(any(Car.class));
    }

    @Test
    public void testDeleteCarById_Found() throws Exception {
        Long carId = 1L;
        Car existingCar = new Car(carId, "Ford", "Focus", "ABC123", 10);

        when(carService.findCarById(carId)).thenReturn(existingCar);
        doNothing().when(carService).deleteCarById(carId);

        mockMvc.perform(delete("/car/{id}", carId))
                .andExpect(status().isOk());

        verify(carService, times(1)).findCarById(carId);
        verify(carService, times(1)).deleteCarById(carId);
    }

    @Test
    public void testDeleteCarById_NotFound() throws Exception {
        Long carId = 2L;

        when(carService.findCarById(carId)).thenReturn(null);

        mockMvc.perform(delete("/car/{id}", carId))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).findCarById(carId);
        verify(carService, never()).deleteCarById(anyLong());
    }

    @Test
    public void testUpdateCar_Found() throws Exception {
        Long carId = 1L;
        Car existingCar = new Car(carId, "Ford", "Focus", "ABC123", 10);
        Car updatedCar = new Car(carId, "Ford", "FocusUpdated", "ABC123", 10);

        when(carService.findCarById(carId)).thenReturn(existingCar);
        when(carService.saveCar(any(Car.class))).thenReturn(updatedCar);

        String updateJson = """
            {
                "brand": "Ford",
                "model": "FocusUpdated",
                "plate": "ABC123",
                "userId": 10
            }
            """;

        mockMvc.perform(put("/car/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNoContent());

        verify(carService, times(1)).findCarById(carId);
        verify(carService, times(1)).saveCar(any(Car.class));
    }

    @Test
    public void testUpdateCar_NotFound() throws Exception {
        Long carId = 3L;

        when(carService.findCarById(carId)).thenReturn(null);

        String updateJson = """
                {
                    "brand": "Ford",
                    "model": "FocusUpdated",
                    "plate": "ABC123",
                    "userId": 10
                }
                """;

        mockMvc.perform(put("/car/{id}", carId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNotFound());

        verify(carService, times(1)).findCarById(carId);
        verify(carService, never()).saveCar(any(Car.class));
    }

    @Test
    public void testFindAllCarsByUserId_Found() throws Exception {
        Long userId = 10L;
        List<Car> cars = List.of(
                new Car(1L, "Ford", "Focus", "ABC123", 10),
                new Car(2L, "Toyota", "Corolla", "XYZ789", 10)
        );

        when(carService.findByUserId(userId)).thenReturn(cars);

        mockMvc.perform(get("/car/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(cars.size()))
                .andExpect(jsonPath("$[0].userId").value(10));
    }

    @Test
    public void testFindAllCarsByUserId_NoContent() throws Exception {
        Long userId = 20L;

        when(carService.findByUserId(userId)).thenReturn(List.of());

        mockMvc.perform(get("/car/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}