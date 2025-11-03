package com.motorbike.service.tests;
import com.motorbike.service.entities.Motorbike;
import com.motorbike.service.services.MotorbikeServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MotorbikeServiceTests {

    @Mock
    private MotorbikeServiceImp motorbikeServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllMotorbikes() {
        List<Motorbike> motorbikes = List.of(
                new Motorbike(1L, "Yamaha", "MT-07", "PLATE123", 10),
                new Motorbike(2L, "Honda", "CB500X", "PLATE456", 20)
        );

        when(motorbikeServiceImp.findAllMotorbikes()).thenReturn(motorbikes);

        List<Motorbike> result = motorbikeServiceImp.findAllMotorbikes();

        assertEquals(2, result.size());
        verify(motorbikeServiceImp).findAllMotorbikes();
    }

    @Test
    void testFindMotorbikeById() {
        Motorbike motorbike = new Motorbike(1L, "Yamaha", "MT-07", "PLATE123", 10);
        when(motorbikeServiceImp.findMotorbikeById(1L)).thenReturn(motorbike);

        Motorbike result = motorbikeServiceImp.findMotorbikeById(1L);

        assertNotNull(result);
        assertEquals("Yamaha", result.getBrand());
        assertEquals("MT-07", result.getModel());
        assertEquals("PLATE123", result.getPlate());
        assertEquals(10, result.getUserId());
        verify(motorbikeServiceImp).findMotorbikeById(1L);
    }

    @Test
    void testSaveMotorbike() {
        Motorbike motorbike = new Motorbike(null, "Yamaha", "MT-07", "PLATE123", 10);
        Motorbike savedMotorbike = new Motorbike(1L, "Yamaha", "MT-07", "PLATE123", 10);

        when(motorbikeServiceImp.saveMotorbike(motorbike)).thenReturn(savedMotorbike);

        Motorbike result = motorbikeServiceImp.saveMotorbike(motorbike);

        assertNotNull(result.getId());
        assertEquals("Yamaha", result.getBrand());
        verify(motorbikeServiceImp).saveMotorbike(motorbike);
    }

    @Test
    void testUpdateMotorbike() {
        Motorbike motorbike = new Motorbike(1L, "Yamaha", "MT-09", "PLATE123", 10);
        when(motorbikeServiceImp.updateMotorbike(motorbike)).thenReturn(motorbike);

        Motorbike result = motorbikeServiceImp.updateMotorbike(motorbike);

        assertNotNull(result);
        assertEquals("MT-09", result.getModel());
        verify(motorbikeServiceImp).updateMotorbike(motorbike);
    }

    @Test
    void testDeleteMotorbikeById() {
        doNothing().when(motorbikeServiceImp).deleteMotorbikeById(1L);

        motorbikeServiceImp.deleteMotorbikeById(1L);

        verify(motorbikeServiceImp).deleteMotorbikeById(1L);
    }

    @Test
    void testFindByUserId() {
        List<Motorbike> motorbikes = List.of(
                new Motorbike(1L, "Yamaha", "MT-07", "PLATE123", 10)
        );
        when(motorbikeServiceImp.findByUserId(10L)).thenReturn(motorbikes);

        List<Motorbike> result = motorbikeServiceImp.findByUserId(10L);

        assertFalse(result.isEmpty());
        verify(motorbikeServiceImp).findByUserId(10L);
    }
}