package com.workoutroutine.service;

import com.workoutroutine.dto.RoutineDto;
import com.workoutroutine.dto.RoutineExerciseDto;
import com.workoutroutine.dto.RoutineRequest;
import com.workoutroutine.model.Routine;
import com.workoutroutine.model.RoutineExercise;
import com.workoutroutine.model.User;
import com.workoutroutine.repository.RoutineRepository;
import com.workoutroutine.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;

    public RoutineService(RoutineRepository routineRepository, UserRepository userRepository) {
        this.routineRepository = routineRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<RoutineDto> listForUser(Long userId) {
        return routineRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoutineDto get(Long userId, Long routineId) {
        Routine routine = routineRepository.findByIdAndUserId(routineId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Routine not found"));
        return toDto(routine);
    }

    public RoutineDto create(Long userId, RoutineRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Routine routine = new Routine(user, req.name(), req.description());
        if (req.exercises() != null) {
            for (int i = 0; i < req.exercises().size(); i++) {
                RoutineExerciseDto dto = req.exercises().get(i);
                RoutineExercise re = new RoutineExercise(
                        routine, dto.exerciseKey(), dto.sets(), dto.reps(), dto.weightKg(), dto.restSeconds(), i);
                routine.getExercises().add(re);
            }
        }
        return toDto(routineRepository.save(routine));
    }

    public RoutineDto update(Long userId, Long routineId, RoutineRequest req) {
        Routine routine = routineRepository.findByIdAndUserId(routineId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Routine not found"));
        routine.setName(req.name());
        routine.setDescription(req.description());
        routine.setUpdatedAt(Instant.now());
        routine.getExercises().clear();
        if (req.exercises() != null) {
            for (int i = 0; i < req.exercises().size(); i++) {
                RoutineExerciseDto dto = req.exercises().get(i);
                RoutineExercise re = new RoutineExercise(
                        routine, dto.exerciseKey(), dto.sets(), dto.reps(), dto.weightKg(), dto.restSeconds(), i);
                routine.getExercises().add(re);
            }
        }
        return toDto(routineRepository.save(routine));
    }

    public void delete(Long userId, Long routineId) {
        Routine routine = routineRepository.findByIdAndUserId(routineId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Routine not found"));
        routineRepository.delete(routine);
    }

    public RoutineDto activate(Long userId, Long routineId) {
        routineRepository.findAllByUserIdAndActiveTrue(userId)
                .forEach(r -> { r.setActive(false); routineRepository.save(r); });
        Routine routine = routineRepository.findByIdAndUserId(routineId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Routine not found"));
        routine.setActive(true);
        return toDto(routineRepository.save(routine));
    }

    private RoutineDto toDto(Routine r) {
        List<RoutineExerciseDto> exercises = r.getExercises().stream()
                .map(e -> new RoutineExerciseDto(
                        e.getId(), e.getExerciseKey(), e.getSets(), e.getReps(),
                        e.getWeightKg(), e.getRestSeconds(), e.getSortOrder()))
                .toList();
        return new RoutineDto(r.getId(), r.getName(), r.getDescription(),
                r.isActive(), r.getCreatedAt(), r.getUpdatedAt(), exercises);
    }
}
