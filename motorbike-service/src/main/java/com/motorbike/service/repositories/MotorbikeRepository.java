package com.motorbike.service.repositories;

import com.motorbike.service.entities.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorbikeRepository extends JpaRepository<Motorbike, Long> {

    List<Motorbike> findByUserId(Long id);
}
