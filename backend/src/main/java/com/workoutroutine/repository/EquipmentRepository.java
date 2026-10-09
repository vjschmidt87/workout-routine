package com.workoutroutine.repository;

import com.workoutroutine.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findAllByUserId(Long userId);
    Optional<Equipment> findByIdAndUserId(Long id, Long userId);
}
