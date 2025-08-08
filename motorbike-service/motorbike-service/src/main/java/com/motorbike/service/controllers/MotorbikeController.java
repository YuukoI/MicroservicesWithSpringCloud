package com.motorbike.service.controllers;

import com.motorbike.service.entities.Motorbike;
import com.motorbike.service.repositories.MotorbikeRepository;
import com.motorbike.service.services.MotorbikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motorbike")
@RequiredArgsConstructor
public class MotorbikeController {

    private final MotorbikeService motorbikeService;

    @GetMapping
    public ResponseEntity<List<Motorbike>> findAllMotorbikes() {
        List<Motorbike> motorbikes = motorbikeService.findAllMotorbikes();
        if(motorbikes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(motorbikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorbike> findMotorbike(@PathVariable Long id) {
        Motorbike motorbike = motorbikeService.findMotorbikeById(id);
        if(motorbike == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(motorbike);
    }

    @PostMapping
    public ResponseEntity<Motorbike> createMotorbike(@Valid @RequestBody Motorbike motorbike) {
        return ResponseEntity.ok(motorbikeService.saveMotorbike(motorbike));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Motorbike> deleteMotorbike(@PathVariable Long id) {
        Motorbike motorbike = motorbikeService.findMotorbikeById(id);
        if(motorbike == null) {
            return ResponseEntity.notFound().build();
        }
        motorbikeService.deleteMotorbikeById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motorbike> updateMotorbike(@PathVariable Long id, @Valid @RequestBody Motorbike motorbike) {
        if(motorbikeService.findMotorbikeById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        motorbike.setId(id);
        Motorbike motorbikeUpdated = motorbikeService.updateMotorbike(motorbike);
        return ResponseEntity.ok().body(motorbikeUpdated);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Motorbike>> findMotorbikesByUserId(@PathVariable Long userId) {
        List<Motorbike> motorbikes = motorbikeService.findByUserId(userId);
        if(motorbikes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(motorbikes);
    }
}

