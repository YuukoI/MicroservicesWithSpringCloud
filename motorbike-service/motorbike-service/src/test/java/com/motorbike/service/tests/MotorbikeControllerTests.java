package com.motorbike.service.tests;

import com.motorbike.service.controllers.MotorbikeController;
import com.motorbike.service.entities.Motorbike;
import com.motorbike.service.services.MotorbikeService;
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

class MotorbikeControllerTests {

    private MockMvc mockMvc;

    @Mock
    private MotorbikeService motorbikeService;

    private MotorbikeController motorbikeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        motorbikeController = new MotorbikeController(motorbikeService);
        mockMvc = MockMvcBuilders.standaloneSetup(motorbikeController).build();
    }

    @Test
    public void testFindAllMotorbikes_Found() throws Exception {
        List<Motorbike> motorbikes = List.of(
                new Motorbike(1L, "Yamaha", "MT-07", "PLATE123", 10),
                new Motorbike(2L, "Honda", "CB500X", "PLATE456", 20)
        );

        when(motorbikeService.findAllMotorbikes()).thenReturn(motorbikes);

        mockMvc.perform(get("/motorbike")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(motorbikes.size()))
                .andExpect(jsonPath("$[0].brand").value("Yamaha"))
                .andExpect(jsonPath("$[1].model").value("CB500X"));
    }

    @Test
    public void testFindAllMotorbikes_NoContent() throws Exception {
        when(motorbikeService.findAllMotorbikes()).thenReturn(List.of());

        mockMvc.perform(get("/motorbike")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFindMotorbikeById_Found() throws Exception {
        Long id = 1L;
        Motorbike motorbike = new Motorbike(id, "Yamaha", "MT-07", "PLATE123", 10);

        when(motorbikeService.findMotorbikeById(id)).thenReturn(motorbike);

        mockMvc.perform(get("/motorbike/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.brand").value("Yamaha"))
                .andExpect(jsonPath("$.plate").value("PLATE123"));
    }

    @Test
    public void testFindMotorbikeById_NotFound() throws Exception {
        Long id = 5L;

        when(motorbikeService.findMotorbikeById(id)).thenReturn(null);

        mockMvc.perform(get("/motorbike/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(motorbikeService, times(1)).findMotorbikeById(id);
    }

    @Test
    public void testCreateMotorbike() throws Exception {
        Motorbike motorbikeToCreate = new Motorbike(null, "Yamaha", "MT-07", "PLATE123", 10);
        Motorbike savedMotorbike = new Motorbike(1L, "Yamaha", "MT-07", "PLATE123", 10);

        when(motorbikeService.saveMotorbike(any(Motorbike.class))).thenReturn(savedMotorbike);

        String motorbikeJson = """
                {
                    "brand": "Yamaha",
                    "model": "MT-07",
                    "plate": "PLATE123",
                    "userId": 10
                }
                """;

        mockMvc.perform(post("/motorbike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(motorbikeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.brand").value("Yamaha"))
                .andExpect(jsonPath("$.userId").value(10));

        verify(motorbikeService, times(1)).saveMotorbike(any(Motorbike.class));
    }

    @Test
    public void testDeleteMotorbike_Found() throws Exception {
        Long id = 1L;
        Motorbike existingMotorbike = new Motorbike(id, "Yamaha", "MT-07", "PLATE123", 10);

        when(motorbikeService.findMotorbikeById(id)).thenReturn(existingMotorbike);
        doNothing().when(motorbikeService).deleteMotorbikeById(id);

        mockMvc.perform(delete("/motorbike/{id}", id))
                .andExpect(status().isNoContent());

        verify(motorbikeService, times(1)).findMotorbikeById(id);
        verify(motorbikeService, times(1)).deleteMotorbikeById(id);
    }

    @Test
    public void testDeleteMotorbike_NotFound() throws Exception {
        Long id = 2L;

        when(motorbikeService.findMotorbikeById(id)).thenReturn(null);

        mockMvc.perform(delete("/motorbike/{id}", id))
                .andExpect(status().isNotFound());

        verify(motorbikeService, times(1)).findMotorbikeById(id);
        verify(motorbikeService, never()).deleteMotorbikeById(anyLong());
    }

    @Test
    public void testUpdateMotorbike_Found() throws Exception {
        Long id = 1L;
        Motorbike existingMotorbike = new Motorbike(id, "Yamaha", "MT-07", "PLATE123", 10);
        Motorbike updatedMotorbike = new Motorbike(id, "Yamaha", "MT-09", "PLATE123", 10);

        when(motorbikeService.findMotorbikeById(id)).thenReturn(existingMotorbike);
        when(motorbikeService.updateMotorbike(any(Motorbike.class))).thenReturn(updatedMotorbike);

        String updateJson = """
                {
                    "brand": "Yamaha",
                    "model": "MT-09",
                    "plate": "PLATE123",
                    "userId": 10
                }
                """;

        mockMvc.perform(put("/motorbike/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("MT-09"));

        verify(motorbikeService, times(1)).findMotorbikeById(id);
        verify(motorbikeService, times(1)).updateMotorbike(any(Motorbike.class));
    }

    @Test
    public void testUpdateMotorbike_NotFound() throws Exception {
        Long id = 3L;

        when(motorbikeService.findMotorbikeById(id)).thenReturn(null);

        String updateJson = """
                {
                    "brand": "Yamaha",
                    "model": "MT-09",
                    "plate": "PLATE123",
                    "userId": 10
                }
                """;

        mockMvc.perform(put("/motorbike/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNotFound());

        verify(motorbikeService, times(1)).findMotorbikeById(id);
        verify(motorbikeService, never()).updateMotorbike(any(Motorbike.class));
    }

    @Test
    public void testFindMotorbikesByUserId_Found() throws Exception {
        Long userId = 10L;
        List<Motorbike> motorbikes = List.of(
                new Motorbike(1L, "Yamaha", "MT-07", "PLATE123", 10),
                new Motorbike(2L, "Honda", "CB500X", "PLATE456", 10)
        );

        when(motorbikeService.findByUserId(userId)).thenReturn(motorbikes);

        mockMvc.perform(get("/motorbike/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(motorbikes.size()))
                .andExpect(jsonPath("$[0].userId").value(10));
    }

    @Test
    public void testFindMotorbikesByUserId_NotFound() throws Exception {
        Long userId = 20L;

        when(motorbikeService.findByUserId(userId)).thenReturn(List.of());

        mockMvc.perform(get("/motorbike/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}