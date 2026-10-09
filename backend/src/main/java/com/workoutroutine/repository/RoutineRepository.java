package com.workoutroutine.repository;

import com.workoutroutine.model.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Routine> findByIdAndUserId(Long id, Long userId);
    List<Routine> findAllByUserIdAndActiveTrue(Long userId);
}
