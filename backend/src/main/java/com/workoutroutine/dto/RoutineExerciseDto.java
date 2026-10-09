package com.workoutroutine.dto;

public record RoutineExerciseDto(
        Long id,
        String exerciseKey,
        int sets,
        int reps,
        Double weightKg,
        int restSeconds,
        int sortOrder
) {}
