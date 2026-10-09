package com.workoutroutine.dto;

import java.time.Instant;
import java.util.List;

public record RoutineDto(
        Long id,
        String name,
        String description,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        List<RoutineExerciseDto> exercises
) {}
