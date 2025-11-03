package com.motorbike.service.services;

import com.motorbike.service.entities.Motorbike;

import java.util.List;

public interface MotorbikeService {

    List<Motorbike> findAllMotorbikes();

    Motorbike findMotorbikeById(Long id);

    Motorbike saveMotorbike(Motorbike motorbike);

    Motorbike updateMotorbike(Motorbike motorbike);

    void deleteMotorbikeById(Long id);

    List<Motorbike> findByUserId(Long id);
}
