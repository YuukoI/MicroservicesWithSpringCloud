package com.motorbike.service.services;

import com.motorbike.service.entities.Motorbike;
import com.motorbike.service.repositories.MotorbikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MotorbikeServiceImp implements MotorbikeService {

    private final MotorbikeRepository motorbikeRepository;

    @Override
    public List<Motorbike> findAllMotorbikes() {
        return motorbikeRepository.findAll();
    }

    @Override
    public Motorbike findMotorbikeById(Long id) {
        return motorbikeRepository.findById(id).orElse(null);
    }

    @Override
    public Motorbike saveMotorbike(Motorbike motorbike) {
        return motorbikeRepository.save(motorbike);
    }

    @Override
    public Motorbike updateMotorbike(Motorbike motorbike) {
        return motorbikeRepository.save(motorbike);
    }

    @Override
    public void deleteMotorbikeById(Long id) {
        motorbikeRepository.deleteById(id);
    }

    @Override
    public List<Motorbike> findByUserId(Long id) {
        return motorbikeRepository.findByUserId(id);
    }
}
