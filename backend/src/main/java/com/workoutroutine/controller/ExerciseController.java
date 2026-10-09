package com.workoutroutine.controller;

import com.workoutroutine.service.ExerciseLibraryService;
import com.workoutroutine.service.ExerciseLibraryService.Exercise;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseLibraryService exerciseLibraryService;

    public ExerciseController(ExerciseLibraryService exerciseLibraryService) {
        this.exerciseLibraryService = exerciseLibraryService;
    }

    @GetMapping
    public List<Exercise> list(
            @RequestParam(required = false) String muscles,
            @RequestParam(required = false) String equipment) {
        List<String> muscleList = muscles != null && !muscles.isBlank()
                ? Arrays.asList(muscles.split(","))
                : null;
        List<String> equipmentList = equipment != null && !equipment.isBlank()
                ? Arrays.asList(equipment.split(","))
                : null;
        return exerciseLibraryService.filter(muscleList, equipmentList);
    }

    @GetMapping("/{key}")
    public Exercise get(@PathVariable String key) {
        return exerciseLibraryService.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Exercise not found: " + key));
    }
}
