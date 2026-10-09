package com.workoutroutine.dto;

import java.util.List;

public record RoutineRequest(String name, String description, List<RoutineExerciseDto> exercises) {}
